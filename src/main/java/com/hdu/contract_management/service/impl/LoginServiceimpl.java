package com.hdu.contract_management.service.impl;

import com.hdu.contract_management.service.LoginService;
import com.hdu.contract_management.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class LoginServiceimpl implements LoginService {
    @Override
    public ResultUtil logout(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return null;
    }

//    @Override
//    public JSONObject authlogin(JSONObject jsonObject) {
//        return null;
//    }
//
//    @Override
//    public JSONObject getUser(String username, String password) {
//        return null;
//    }
//
//    @Override
//    public JSONObject getInfo() {
//        return null;
//    }
//
//    @Override
//    public JSONObject logout() {
//        return null;
//    }
}
