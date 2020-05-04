package com.hdu.contract_management.controller;


import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.Record;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.ReceiptService;
import com.hdu.contract_management.service.RecordService;
import com.hdu.contract_management.service.UserService;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 发票 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-05-01
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @PostMapping("")
    public ResultUtil createReceipt(Receipt receipt) {
        receiptService.createReceipt(receipt);
        System.out.println(receipt.toString());
        return ResultUtil.success("新增发票申请成功");
    }

    @GetMapping("")
    public ResultUtil getReceiptList(Integer userId) {
        return ResultUtil.success("查询发票列表成功", receiptService.getReceipt(userId));
    }

    @GetMapping("/")
    public ResultUtil getReceipt(Integer receiptId) {
        return ResultUtil.success("查询发票信息成功", receiptService.getById(receiptId));
    }

}

