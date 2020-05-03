package com.hdu.contract_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.utils.ResultUtil;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-03-11
 */
public interface UserService extends IService<User> {
     void trans(Integer id, String password);

     User selectUserByUsername(String username);

     String selectByName(String userName);

     Map getNameAndDepartmentById(Integer id);

     Integer getRandomPeople(Integer departmentId, Boolean isLeader);
}
