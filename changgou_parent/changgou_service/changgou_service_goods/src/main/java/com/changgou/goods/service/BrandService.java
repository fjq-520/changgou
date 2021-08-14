package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /**
     * 根據id，查詢單個
     * @param id
     * @return
     */
    public Brand selectById(long id);

    /**
     * 增加
     * @param brand
     * @return
     */
    public boolean add(Brand brand);

    /**
     * 修改
     * @param brand
     * @param id
     * @return
     */
    public boolean update(Brand brand,long id);

    /**
     * 刪除
     * @param id
     * @return
     */
    public boolean delete(long id);

    /**
     * 查詢所有
     * @return
     */
    public List<Brand> selectAll();

    /**
     * 根據條件查詢
     * @param map
     * @return
     */
    public List<Brand> selectList(Map<String,Object> map);

    /**
     * 分页查询
     * @param pageNum
     * @param size
     * @return
     */
    public Page<Brand> findPage(Integer pageNum,Integer size);

    /**
     * 按条件+分页查询
     * @return
     */
    public Page<Brand> findPageAndList(Map<String,Object> map,Integer pageNum,Integer pageSize);

    /**
     * 根据分类名称查询品牌列表
     * @param categoryName
     * @return
     */
    public List<Brand> selectListByCategoryName( String categoryName);

    /**
     * 根据分类名称查询规格列表
     * @param categoryName
     * @return
     */
    public List<Spec> selectSpecByCategoryName( String categoryName);
}
