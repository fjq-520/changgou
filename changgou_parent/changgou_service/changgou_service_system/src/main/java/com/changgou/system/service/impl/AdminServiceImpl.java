package com.changgou.system.service.impl;

import com.changgou.system.dao.AdminDao;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public boolean add(Admin admin) {
        String password = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(password);
        admin.setStatus("1");
        return adminDao.insert(admin)>0?true:false;
    }

    @Override
    public boolean login(Admin admin) {
        Admin admin1 = new Admin();
        admin1.setStatus("1");
        admin1.setLoginName(admin.getLoginName());
        Admin admin2 = adminDao.selectOne(admin1);
        if (admin2==null){
            return false;
        }else {
            boolean checkpw = BCrypt.checkpw(admin.getPassword(),admin2.getPassword() );
            return checkpw;
        }
    }
}
