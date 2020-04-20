package com.hdu.contract_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.utils.ResultUtil;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyq
 * @since 2020-03-11
 */
public interface UserService extends IService<User> {
    public void trans(Integer id, String password);
    public User selectUserByUsername(String username);
    public String selectByName(String userName);
    public Map getNameAndDepartmentById(Integer id);
    }
