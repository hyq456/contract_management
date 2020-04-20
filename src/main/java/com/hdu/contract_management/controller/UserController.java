package com.hdu.contract_management.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.mapper.UserMapper;
import com.hdu.contract_management.service.UserService;
import com.hdu.contract_management.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.HanaSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.PastOrPresent;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserService userService;

    @GetMapping("")
    public ResultUtil getUser(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        User user = userService.getById(id);
        return ResultUtil.success("查询用户成功", user);
    }

    @PostMapping("")
    public ResultUtil updateUserInfo(@RequestParam(value = "tel") String tel,
                                     @RequestParam(value = "email") String email,
                                     @RequestParam(value = "id") Integer id,
                                     @RequestParam(value = "newPw", required = false) String newPw) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("email", email).set("tel", tel);
        if (newPw != null)
            wrapper.set("password", newPw);
        if (userService.update(wrapper))
            return ResultUtil.success("保存成功");
        else
            return ResultUtil.error("保存失败，请重试");

    }

    @GetMapping("/{id}")
    public ResultUtil getUserByUserId(@PathVariable(value = "id") Integer id) {
        User user = userService.getById(id);
        return ResultUtil.success("查询用户成功", user);
    }

    @GetMapping("/nameanddepartment")
    public ResultUtil getNameAndDepartmentById(Integer id) {
        Map<String, Object> map = userService.getNameAndDepartmentById(id);
        return ResultUtil.success("查询名称及单位成功", map);
    }


}
