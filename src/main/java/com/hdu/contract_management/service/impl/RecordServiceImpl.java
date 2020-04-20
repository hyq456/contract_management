package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Record;
import com.hdu.contract_management.mapper.RecordMapper;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-03-30
 */

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired(required = false)
    RecordMapper recordMapper;
    @Autowired
    ContractService contractService;


    @Override
    public List<Record> getRecordsbyContract(Integer contractId) {

        return null;
    }

    @Override
    public LinkedHashMap getMonthSale(String toyear,Integer id) {
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_people",id);
        List<Contract> contracts = contractService.list(queryWrapper);
        List list = new ArrayList();
        for (Contract c:contracts) {
            list.add(c.getId());
        }
        if(list.isEmpty()){
            return new LinkedHashMap();
        }
        return recordMapper.getMonthSale(toyear,list);
    }

    @Override
    public LinkedHashMap getMonthPurchase(String toyear,Integer id) {
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_people",id);
        List<Contract> contracts = contractService.list(queryWrapper);
        List list = new ArrayList();
        for (Contract c:contracts) {
            list.add(c.getId());
        }
        if(list.isEmpty()){
            return new LinkedHashMap();
        }
        return recordMapper.getMonthPurchase(toyear,list);

    }

}
