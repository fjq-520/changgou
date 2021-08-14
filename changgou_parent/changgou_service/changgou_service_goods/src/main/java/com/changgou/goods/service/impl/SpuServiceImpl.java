package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.util.IdWorker;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.BrandService;
import com.changgou.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加商品
     * @param goods
     */
    @Override
    public void add(Goods goods) {
        Spu spu = goods.getSpu();
        //分布式生成id算法
        long spuId = idWorker.nextId();
        spu.setId(String.valueOf(spuId));
        //设置删除状态
        spu.setIsDelete("0");
        //设置上架状态
        spu.setIsMarketable("0");
        //设置审核状态
        spu.setStatus("0");
        spuMapper.insertSelective(spu);
        //保存sku集合到数据库
        saveSkuList(goods);

    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @Override
    public Goods findGoodsById(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /**
     * 根据id删除,逻辑删除不是物理删除
     * @param id
     */
    @Override
    public void deleteById(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!spu.getIsDelete().equals("0")){
            throw new RuntimeException("该商品只有处于下架状态才能被删除");
        }
        spu.setIsDelete("1");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 修改商品
     * @param goods
     */
    @Override
    public void update(Goods goods) {
        Spu spu = goods.getSpu();
        spuMapper.updateByPrimaryKey(spu);
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",spu.getId());
        skuMapper.deleteByExample(example);
        this.saveSkuList(goods);
    }

    /**
     * 审核商品
     * @param id
     */
    @Override
    public void audit(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu==null){
            throw new RuntimeException("该商品不存在");
        }
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品属于删除状态");
        }
        //不处于删除状态,修改审核状态为1,上下架状态为1
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 下架商品
     * @param id
     */
    @Override
    public void pull(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu==null){
            throw new RuntimeException("该商品不存在");
        }
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("该商品处于删除状态");
        }
        //商品处于未删除状态的话,则修改上下架状态为已下架(0)
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);

    }

    /**
     * 上架商品
     * @param id
     */
    @Override
    public void put(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu==null){
            throw new RuntimeException("该商品不存在");
        }
        if ("1".equals(spu.getStatus())){
            throw new RuntimeException("未通过审核的商品不能上架");
        }
        //设置商品上架，1
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 物理删除
     * @param id
     */
    @Override
    public void delete(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if ("1".equals(spu.getIsMarketable())){
            throw new RuntimeException("该商品还未下架，不准删除");
        }
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("此商品不是删除的列表");
        }
        spuMapper.deleteByPrimaryKey(id);


    }

    /**
     * 还原被删除的商品
     * @param id
     */
    @Override
    public void restore(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getIsDelete())){
            throw new RuntimeException("该商品未被删除");
        }
        spu.setStatus("0");//未审核
        spu.setIsDelete("0");//设置为未删除
        spuMapper.updateByPrimaryKeySelective(spu);

    }

    private void saveSkuList(Goods goods) {
        //获取spu
        Spu spu = goods.getSpu();
        //获取时间
        Date date = new Date();
        //获取分类对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        //获取brand对象
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        //获取sku
        List<Sku> skuList = goods.getSkuList();
        if (skuList!=null){
            for (Sku sku : skuList) {
                //设置sku主键
                sku.setId(String.valueOf(idWorker.nextId()));
                //设置sku规格
                if (sku.getSpec()==null||"".equals(sku.getSpec())){
                    sku.setSpec("{}");
                }
                //设置sku名称（商品名称+规格）
                String name = spu.getName();
                //将规格json转换为map,将map中的value进行名称的拼接
                Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                if (specMap != null && specMap.size()>0){
                    for (String value : specMap.values()) {
                        name+=" "+value;
                    }
                }
                sku.setName(name);
                //设置spuid
                sku.setSpuId(spu.getId());
                //设置创建与修改时间
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                //商品分类id
                sku.setCategoryId(category.getId());
                //设置商品分类名称
                sku.setCategoryName(category.getName());
                //设置品牌名称
                sku.setBrandName(brand.getName());
                //将sku添加到数据库
                skuMapper.insertSelective(sku);
            }
        }
    }
}
