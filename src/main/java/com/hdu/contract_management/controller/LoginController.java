package com.hdu.contract_management.controller;

import com.hdu.contract_management.utils.ResultUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.apache.shiro.SecurityUtils.getSubject;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/auth")
    public Map<String, Object> login(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:" + username + "       password:" + password);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 404);
        result.put("msg", "登录失败");

        // 从SecurityUtils里边创建一个 subject
        Subject subject = getSubject();
        // 在认证提交前准备 token（令牌）

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
            System.out.println(subject.getPrincipal());
        } catch (UnknownAccountException uae) {
            result.put("msg", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            result.put("msg", "密码不正确");
        } catch (LockedAccountException lae) {
            result.put("msg", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            result.put("msg", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            result.put("msg", "密码不正确");
        }
        if (subject.isAuthenticated()) {
            result.put("msg", "登录成功");
            result.put("token", subject.getSession().getId());
            result.put("user", subject.getPrincipal());
            result.put("code", 200);
        } else {
            token.clear();
        }
        return result;
    }

    @RequestMapping("/logout")
    public ResultUtil logout(HttpServletRequest request) {
        Subject subject = getSubject();
//        System.out.println(subject.getPrincipal());
        subject.logout();
//        System.out.println(subject.getPrincipal());
        return ResultUtil.success("退出成功");


    }

}
