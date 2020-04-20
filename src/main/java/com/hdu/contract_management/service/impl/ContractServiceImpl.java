package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Review;
import com.hdu.contract_management.mapper.ContractMapper;
import com.hdu.contract_management.service.ContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.service.ReviewService;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * <p>
 * 合同表 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-03-22
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ContractService contractService;
    @Autowired
    ContractMapper contractMapper;

    @Override
    public Contract modifyContract(Contract contract) {
        int count = reviewService.reviewCount(contract.getId());
        contract.setContractState(count);
        contractService.updateById(contract);
        return contract;
    }

    @Override
    public Map<String, Object> getSaleDate(Integer userId) {
        QueryWrapper<Contract> wrapper = new QueryWrapper<>();
        wrapper.eq("sign_people",userId);
        wrapper.eq("contract_state",2);
        wrapper.eq("contract_type",1);
        List<Contract> contracts = contractService.list(wrapper);
        //总
        int total = 0;
        //未完成
        int todo = 0;
        List totalList = new ArrayList();
        List todoList = new ArrayList();
        for (Contract c:contracts) {
            total += c.getTotal();
            todo += c.getRemainder();
            Map<String,Object> totalMap = new HashMap<>();
            totalMap.put("value",c.getTotal());
            totalMap.put("name",c.getName());
            totalList.add(totalMap);
            Map<String,Object> todoMap = new HashMap<>();
            todoMap.put("value",c.getRemainder());
            todoMap.put("name",c.getName());
            todoList.add(todoMap);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("todo",todo);
        result.put("totalList",totalList);
        result.put("todoList",todoList);

        return result;
    }

    @Override
    public Map<String, Object> getPurchaseDate(Integer userId) {
        QueryWrapper<Contract> wrapper = new QueryWrapper<>();
        wrapper.eq("sign_people",userId);
        wrapper.eq("contract_state",2);
        wrapper.eq("contract_type",0);
        List<Contract> contracts = contractService.list(wrapper);
        //总
        int total = 0;
        //未完成
        int todo = 0;
        List totalList = new ArrayList();
        List todoList = new ArrayList();
        for (Contract c:contracts) {
            total += c.getTotal();
            todo += c.getRemainder();
            Map<String,Object> totalMap = new HashMap<>();
            totalMap.put("value",c.getTotal());
            totalMap.put("name",c.getName());
            totalList.add(totalMap);
            Map<String,Object> todoMap = new HashMap<>();
            todoMap.put("value",c.getRemainder());
            todoMap.put("name",c.getName());
            todoList.add(todoMap);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("total",total);
        result.put("todo",todo);
        result.put("totalList",totalList);
        result.put("todoList",todoList);

        return result;
    }

    @Override
    public Map<String, Object> expire(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        List<Contract> expire = contractMapper.getExpire(userId);

        List list = new ArrayList();
        LocalDate from = LocalDate.now();
        for (Contract c:expire) {
            Map<String,Object> map = new HashMap<>();
            LocalDate to = c.getStopDate();
            int days = (int) ChronoUnit.DAYS.between(from, to);
            map.put("name",c.getName());
            map.put("days",days);
            list.add(map);
        }

        result.put("expireData",list);
        result.put("expireNum",expire.size());
        return result;
    }
}
