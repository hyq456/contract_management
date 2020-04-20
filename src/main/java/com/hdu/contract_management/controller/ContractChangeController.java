package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.ContractChange;
import com.hdu.contract_management.service.ContractChangeService;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.ReviewProgressService;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 合同变更表 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-04-04
 */
@RestController
@RequestMapping("/contractChange")
public class ContractChangeController {
    @Autowired
    ContractService contractService;
    @Autowired()
    ContractChangeService contractChangeService;
    @Autowired
    ReviewProgressService reviewProgressService;

    @GetMapping("")
    public ResultUtil getContractChange(Integer contractId){
        QueryWrapper<ContractChange> wrapper = new QueryWrapper<>();
        wrapper.eq("contract_id",contractId);
        ContractChange contractChange = contractChangeService.getOne(wrapper);
        return ResultUtil.success("获取合同变更记录成功",contractChange);
    }

    @PostMapping("")
    public ResultUtil subContratChange(HttpServletRequest request){
        ContractChange contractChange = new ContractChange();
        contractChange.setContractId(Integer.parseInt(request.getParameter("id")));
        contractChange.setName(request.getParameter("contract_name"));
        contractChange.setPartyB(request.getParameter("party_b"));
        contractChange.setFilePath(request.getParameter("filepath"));
        contractChange.setDepartment(Integer.parseInt(request.getParameter("department")));
        contractChange.setSignPeople(Integer.parseInt(request.getParameter("sign_people")));
        contractChange.setContractType(Integer.parseInt(request.getParameter("type")));
        contractChange.setTotal(Integer.parseInt(request.getParameter("total")));
        contractChange.setRemainder(Integer.parseInt(request.getParameter("total")));
        contractChange.setContractDescribe(request.getParameter("describe"));
        contractChange.setStartDate(LocalDate.parse(request.getParameter("start_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contractChange.setStopDate(LocalDate.parse(request.getParameter("stop_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contractChange.setChangeDescribe(request.getParameter("change_describe"));
        contractChange.setChangeType(Integer.parseInt(request.getParameter("change_type")));

        Contract contract = contractService.getById(contractChange.getContractId());

        contractChange.setRemainder(contract.getRemainder());
        UpdateWrapper<Contract> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",contract.getId()).set("contract_state",1);
        contractService.update(wrapper);
        reviewProgressService.increaseProgressByChangeContract(contract);
        if(contractChangeService.save(contractChange))

            return ResultUtil.success("保存合同变更成功");
        else
            return ResultUtil.error("保存变更失败");

    }

    @PostMapping("/stop")
    public ResultUtil stopContract(HttpServletRequest request){
        Integer contractid = Integer.parseInt(request.getParameter("id"));
        String reason = request.getParameter("reason");
        Contract contract = contractService.getById(contractid);
        contract.setContractState(1);
        contractService.updateById(contract);
        reviewProgressService.increaseProgressByStopContract(contract);
        ContractChange contractChange = new ContractChange();
        contractChange.setContractId(contractid);
        contractChange.setChangeDescribe(reason);
        contractChange.setChangeType(2);
        contractChangeService.save(contractChange);
        return ResultUtil.success("已申请合同终止");
    }


}

