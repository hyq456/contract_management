package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.Departments;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.mapper.DepartmentsMapper;
import com.hdu.contract_management.mapper.UserMapper;
import com.hdu.contract_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-03-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private DepartmentsMapper departmentsMapper;

    @Override
    public void trans(Integer id,String password){
        User selectById = userMapper.selectById(id);
        selectById.setPassword(password);
        userMapper.updateById(selectById);
    }

    @Override
    public User selectUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public String selectByName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        System.out.println(userMapper.selectOne(queryWrapper));
        User user = userMapper.selectOne(queryWrapper);

        return user.getPassword();
    }

    @Override
    public Map<String, Object> getNameAndDepartmentById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        User user = userMapper.selectById(id);
        Departments departments = departmentsMapper.selectById(user.getDepartment());
        map.put("username",user.getRealname());
        map.put("department",departments.getDepartmentName());
        return map;
    }


}
