package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequestMapping("/spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods goods){
            spuService.add(goods);
            return new Result(true, StatusCode.OK,"添加成功！！！");
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id){
        Goods good = spuService.findGoodsById(id);
        return new Result(true,StatusCode.OK,"查询成功！！！",good);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id){
        spuService.deleteById(id);
        return new Result(true,StatusCode.OK,"逻辑删除成功");
    }

    /**
     * 审核商品
     * @param id
     * @return
     */
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable("id") String id){
            spuService.audit(id);
            return new Result(true,StatusCode.OK,"商品审核成功");
    }

    /**
     * 下架商品
     * @param id
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable("id") String id){
        spuService.pull(id);
        return new Result(true,StatusCode.OK,"下架成功");
    }

    /**
     * 上架商品
     * @param id
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable("id") String id){
        spuService.put(id);
        return new Result(true,StatusCode.OK,"上架成功");
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @DeleteMapping("/realDelete/{id}")
    public Result realDelete(@PathVariable("id") String id){
        spuService.delete(id);
        return new Result(true,StatusCode.OK,"物理删除成功");
    }

    /**
     * 还原被删除的商品
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable("id") String id){
        spuService.restore(id);
        return new Result(true,StatusCode.OK,"已还原删除的商品");
    }
}
