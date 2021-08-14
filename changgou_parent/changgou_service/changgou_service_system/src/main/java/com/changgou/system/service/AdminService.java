package com.changgou.system.service;

import com.changgou.system.pojo.Admin;

public interface AdminService {
    /**
     * 注册账户密码
     * @param admin
     * @return
     */
    public boolean add(Admin admin);

    /**
     * 用户登录
     * @param admin
     * @return
     */
    public boolean login(Admin admin);
}
