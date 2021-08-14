package com.changgou.goods.dao;


import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BrandMapper extends Mapper<Brand> {
    @Select("SELECT name,image FROM " +
            "tb_brand WHERE id  IN (SELECT brand_id FROM tb_category_brand WHERE  category_id " +
            "IN (SELECT id FROM tb_category WHERE NAME=#{name}) )order by seq")
    public List<Brand> selectListByCategoryName(@Param("name") String categoryName);

    @Select("SELECT ts.name,ts.`options` FROM tb_spec ts WHERE template_id IN(SELECT id FROM tb_template WHERE NAME = #{categoryName})")
    public List<Spec> selectSpecByCategoryName(@Param("categoryName") String categoryName);
}
