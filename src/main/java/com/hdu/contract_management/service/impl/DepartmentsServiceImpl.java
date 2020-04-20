package com.hdu.contract_management.service.impl;

import com.hdu.contract_management.entity.Departments;
import com.hdu.contract_management.mapper.DepartmentsMapper;
import com.hdu.contract_management.service.DepartmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments> implements DepartmentsService {

}
