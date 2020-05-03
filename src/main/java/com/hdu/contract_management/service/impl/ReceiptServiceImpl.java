package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.ReceiptApprove;
import com.hdu.contract_management.entity.Record;
import com.hdu.contract_management.mapper.ReceiptMapper;
import com.hdu.contract_management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * <p>
 * 发票 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-05-01
 */
@Service
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {

    @Autowired
    RecordService recordService;
    @Autowired
    ContractService contractService;
    @Autowired
    UserService userService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptApproveService approveService;

    @Override
    public boolean createReceipt(Receipt receipt) {
        Record record = recordService.getById(receipt.getRecordId());
        if (record.getType() == 0) { // 收款 开发票
            Contract contract = contractService.getById(record.getContractId());
            Integer secondOperator = userService.getRandomPeople(contract.getDepartment(), true);
            receipt.setSecondOperator(secondOperator);
            receipt.setType(1);
            receiptService.save(receipt);
            record.setReceipt(2);
            recordService.updateById(record);

            receipt = receiptService.getOne(new QueryWrapper<Receipt>()
                    .eq("partyB", receipt.getPartyB())
                    .eq("amount", receipt.getAmount())
                    .eq("receipt_name", receipt.getReceiptName())
                    .last("limit 1"));
            ReceiptApprove receiptApprove = new ReceiptApprove();
            receiptApprove.setApprovePeople(secondOperator);
            receiptApprove.setReceiptId(receipt.getId());
            approveService.save(receiptApprove);
            // TODO: 2020/5/3 发通知
        } else { // 付款 收发票
            Integer operator = userService.getRandomPeople(1, false);
            receipt.setOperator(operator);
            receiptService.save(receipt);
            record.setReceipt(2);
            recordService.updateById(record);
            receipt = receiptService.getOne(new QueryWrapper<Receipt>()
                    .eq("receipt_number", receipt.getReceiptNumber())
                    .last("limit 1"));
            ReceiptApprove receiptApprove = new ReceiptApprove();
            receiptApprove.setApprovePeople(operator);
            receiptApprove.setReceiptId(receipt.getId());
            approveService.save(receiptApprove);
            // TODO: 2020/5/3 发通知

        }
        return true;
    }
}
