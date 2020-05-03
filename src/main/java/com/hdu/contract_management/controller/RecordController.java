package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Record;
import com.hdu.contract_management.mapper.RecordMapper;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.RecordService;
import com.hdu.contract_management.utils.ResultUtil;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;
    @Autowired
    ContractService contractService;

    @GetMapping("/{id}")
    public ResultUtil getRecordsByContractId(@PathVariable(value = "id") Integer contractId) {
        List<Record> records = recordService.list(new QueryWrapper<Record>().eq("contract_id", contractId).orderByDesc("time"));
        if (records != null) {
            return ResultUtil.success("查询履行记录成功", records);
        } else
            return ResultUtil.error("查询履行记录失败");
    }

    @GetMapping("")
    public ResultUtil getRecord(Integer id) {
        return ResultUtil.success("查询履行记录成功", recordService.getById(id));
    }

    @PostMapping("/")
    public ResultUtil newRecord(HttpServletRequest request) {
        Record record = new Record();
        record.setContractId(Integer.parseInt(request.getParameter("contract_id")));
        record.setName(request.getParameter("name"));
        record.setNumber(Integer.parseInt(request.getParameter("number")));
        record.setTime(LocalDate.parse(request.getParameter("time"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        record.setType(Integer.parseInt(request.getParameter("type")));
        record.setMore(request.getParameter("more"));
        Contract contract = contractService.getById(record.getContractId());
        contract.setRemainder(contract.getRemainder() - record.getNumber());
        if (contract.getRemainder() >= 0) {
            contractService.updateById(contract);
            if (recordService.save(record)) {
                return ResultUtil.success("新增记录成功");
            } else
                return ResultUtil.error("新增记录失败");
        } else
            return ResultUtil.error("新增记录失败，输入金额大于待结算金额");

    }

    @GetMapping("/getMonthRecord")
    public ResultUtil getMonthRecord(@RequestParam(value ="toyear",required = false,defaultValue = "2020")String year,

                                    @RequestParam(value="id")Integer id){
        LinkedHashMap monthSale = recordService.getMonthSale(year,id);
        LinkedHashMap monthPurchase = recordService.getMonthPurchase(year,id);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("monthSale",monthSale.values());
        hashMap.put("monthPurchase",monthPurchase.values());
        return ResultUtil.success("查询每月记录成功",hashMap);
    }

}

