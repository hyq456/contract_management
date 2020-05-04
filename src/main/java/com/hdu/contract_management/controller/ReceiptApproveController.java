package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.ReceiptApprove;
import com.hdu.contract_management.service.ReceiptApproveService;
import com.hdu.contract_management.service.ReceiptService;
import com.hdu.contract_management.utils.ResultUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 发票审批流程 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-05-03
 */
@RestController
@RequestMapping("/receiptApprove")
public class ReceiptApproveController {
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptApproveService approveService;

    /**
     * 获取待用户审批的发票
     *
     * @param userId 用户id
     * @return 待审批发票封装的list
     */
    @GetMapping("")
    public ResultUtil getReceiptApproveList(Integer userId) {
        return ResultUtil.success("查询待审批发票列表成功", approveService.getReceiptApproveList(userId));
    }

    @PostMapping("/leader")
    public ResultUtil leaderFinishApprove(Integer approveId) {
        approveService.leaderFinish(approveId);
        return ResultUtil.success("负责人发票审批完成");
    }

    @PostMapping("/finance")
    public ResultUtil financeFinishApprove(Integer approveId, Integer receiptNumber,
                                           Integer receiptCode, String receiptDate) {
        LocalDate date =
                LocalDate.parse(receiptDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Receipt receipt = new Receipt();
        receipt.setReceiptNumber(receiptNumber);
        receipt.setReceiptCode(receiptCode);
        receipt.setReceiptDate(date);
        approveService.financeFinish(approveId, receipt);
        return ResultUtil.success("财务部发票审批完成");
    }
}

