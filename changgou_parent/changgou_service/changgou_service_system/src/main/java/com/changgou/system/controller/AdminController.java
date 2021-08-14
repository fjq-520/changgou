package com.changgou.system.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;
import com.changgou.system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping
    public Result add(@RequestBody Admin admin){
        if (admin==null){
            return new Result(false, StatusCode.ERROR,"注册失败！！！");
        }
        boolean add = adminService.add(admin);
        return new Result(true,StatusCode.OK,"注册成功！！！");

    }
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        boolean login = adminService.login(admin);
        if (login) {
            Map<String, String> info = new HashMap<>();
            info.put("username",admin.getLoginName());
            //生成token
            String token = JwtUtil.creatJwt(UUID.randomUUID().toString(), admin.getLoginName(), null);
            info.put("token",token);
            return new Result(true, StatusCode.OK, "恭喜你，登录成功！！！",info);
        }
        return new Result(false,StatusCode.ERROR,"登录失败！！！,用户名或密码错误");
    }
}
