package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;

public interface SpuService {
    /**
     * 添加商品
     * @param goods
     */
    public void add(Goods goods);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    public Goods findGoodsById(String id);

    /**
     * 删除，逻辑删除
     * @param id
     */
    public void deleteById(String id);

    /**
     * 修改商品
     * @param goods
     */
    public void update(Goods goods);

    /**
     * 审核商品，为1则为删除状态，审核状态为0表示未审核
     */
    public void audit(String id);

    /**
     * 下架商品
     * @param id
     */
    public void pull(String id);

    /**
     * 上架商品
     * @param id
     */
    public void put(String id);

    /**
     * 物理删除
     * @param id
     */
    public void delete(String id);

    /**
     * 还原删除的商品
     * @param id
     */
    public void restore(String id);
}
