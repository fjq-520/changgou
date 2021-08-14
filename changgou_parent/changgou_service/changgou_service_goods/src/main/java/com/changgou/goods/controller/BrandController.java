package com.changgou.goods.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("/brand/{id}")
    public Result<Brand> selectById(@PathVariable("id") long id){
        Brand brand = brandService.selectById(id);
        if (brand==null){
            throw new RuntimeException();
        }
        return new Result<>(true,StatusCode.OK,"查詢成功",brand);
    }
    @PostMapping("/brand")
    public Result add(@RequestBody Brand brand){
        boolean add = brandService.add(brand);
        if (!add){
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @PutMapping("/brand/{id}")
    public Result<Brand> update(@RequestBody Brand brand,@PathVariable("id") long id){
        boolean update = brandService.update(brand, id);
        if (!update){
            throw new RuntimeException();
        }
        return new Result<>(true,StatusCode.OK,"修改成功");
    }
    @DeleteMapping("/brand/{id}")
    public Result<Brand> deleteById(@PathVariable("id") long id){
        boolean delete = brandService.delete(id);
        if (!delete){
            throw new RuntimeException();
        }
        return new Result<>(true,StatusCode.OK,"刪除成功");
    }
    @GetMapping("/brand")
    public Result<Brand> selectAll(){
        List<Brand> brands = brandService.selectAll();
        if (brands==null){
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK,"查詢所有成功",brands);
    }

    /**
     * 多条件查询
     * @param map
     * @return
     */
    @GetMapping("/brand/search")
    public Result selectList(@RequestParam Map<String,Object> map){
        List<Brand> brands = brandService.selectList(map);
        if (brands==null){
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK,"按條件查詢成功",  brands);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/brand/{page}/{size}")
    public Result findPage(@PathVariable("page") Integer pageNum,@PathVariable("size") Integer size){
        Page<Brand> page = brandService.findPage(pageNum, size);
        PageResult<Brand> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        //PageInfo<Brand> pageInfo = new PageInfo<>(page);
        return new Result(true,StatusCode.OK,"分页查询成功",pageResult);
    }

    /**
     * 多条件+分页查询
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/brand/search/{pageNum}/{pageSize}")
    public Result findPageAndList(@RequestParam Map<String,Object> map,@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        Page<Brand> pageAndList = brandService.findPageAndList(map, pageNum, pageSize);
        PageResult<Brand> pageResult = new PageResult<>(pageAndList.getTotal(),pageAndList.getResult());
        return new Result(true,StatusCode.OK,"条件分页查询成功",pageResult);
    }

    /**
     * 根据商品分类查询品牌列表
     * @param categoryName
     * @return
     */
    @GetMapping("/brand/category/{categoryName}")
    public Result selectListByCategoryName(@PathVariable("categoryName") String categoryName){
        List<Brand> brands = brandService.selectListByCategoryName(categoryName);
        return new Result(true,StatusCode.OK,"查询成功",brands);
    }

    /**
     * 根据商品分类查询品牌列表
     * @param categoryName
     * @return
     */
    @GetMapping("/spec/category/{categoryName}")
    public Result selectSpecByCategoryName(@PathVariable("categoryName") String categoryName){
        List<Spec> specs = brandService.selectSpecByCategoryName(categoryName);
        return new Result(true,StatusCode.OK,"查询成功",specs);
    }


}
