package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 按品牌id查询单个品牌
     * @param id
     * @return
     */
    @Override
    public Brand selectById(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     * @param brand
     * @return
     */
    @Override
    public boolean add(Brand brand) {
        return brandMapper.insertSelective(brand)>0?true:false;
    }

    /**
     * 修改
     * @param brand
     * @param id
     * @return
     */
        @Override
        public boolean update (Brand brand,long id){
            return brandMapper.updateByPrimaryKeySelective(brand)>0?true:false;
        }

    /**
     * 删除
      * @param id
     * @return
     */
    @Override
        public boolean delete ( long id){
            return brandMapper.deleteByPrimaryKey(id)>0?true:false;
        }

    /**
     * 查询所有品牌
     * @return
     */
        @Override
        public List<Brand> selectAll () {
            return brandMapper.selectAll();
        }

    /**
     * 根据条件查询品牌
     * @param map
     * @return
     */
    @Override
    public List<Brand> selectList(Map<String, Object> map) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (map!=null) {
            //品牌名稱查詢
            if (map.get("name") != null && "".equals(map.get("name"))) {
                criteria.andLike("name", "%" + map.get("name") + "%");
            }
            //品牌首字母查詢
            if (map.get("letter") != null && "".equals(map.get("letter"))) {
                criteria.andEqualTo("letter", map.get("letter"));
            }
        }
        return brandMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public Page<Brand> findPage(Integer pageNum, Integer size) {
        PageHelper.startPage(pageNum,size);
        List<Brand> brands = brandMapper.selectAll();
        return (Page<Brand>) brands;
    }

    /**
     * 按条件+分页查询
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Brand> findPageAndList(Map<String, Object> map, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (map!=null){
            //模糊查询
            if (map.get("name") != null && "".equals(map.get("name"))){
                criteria.andLike("naem","%"+map.get("name")+"%");
            }
            //首字母
            if (map.get("letter") != null && "".equals(map.get("letter"))){
                criteria.andEqualTo("letter",map.get("letter"));
            }
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        return (Page<Brand>) brands;
    }

    /**
     * 根据分类名称查询品牌列表
     * @param categoryName
     * @return
     */
    @Override
    public List<Brand> selectListByCategoryName(String categoryName) {
        List<Brand> brands = brandMapper.selectListByCategoryName(categoryName);
        return brands;
    }

    /**
     * 根据分裂名称查询规格列表
     * @param categoryName
     * @return
     */
    @Override
    public List<Spec> selectSpecByCategoryName(String categoryName) {
        return brandMapper.selectSpecByCategoryName(categoryName);
    }


}
