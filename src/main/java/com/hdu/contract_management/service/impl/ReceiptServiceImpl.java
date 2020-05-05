package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.*;
import com.hdu.contract_management.entity.vo.WorkRecordVo;
import com.hdu.contract_management.mapper.ReceiptMapper;
import com.hdu.contract_management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ObjIntConsumer;

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
    @Autowired
    DingdingService dingdingService;

    @Override
    public boolean createReceipt(Receipt receipt) {
        Record record = recordService.getById(receipt.getRecordId());
        if (record.getType() == 0) { // 收款 开发票
            Contract contract = contractService.getById(record.getContractId());
            Integer secondOperator = userService.getRandomPeople(contract.getDepartment(), true);
            receipt.setSecondOperator(secondOperator);
            receipt.setType(0);
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
            //发送钉钉提醒
            User user = userService.getById(secondOperator);
            WorkRecordVo workRecordVo =
                    new WorkRecordVo("有一份发票待审批", "名称", contract.getName() + receipt.getReceiptName(), user);
            dingdingService.sendWorkRecord(workRecordVo);
        } else { // 付款 收发票
            Integer operator = userService.getRandomPeople(1, false);
            receipt.setOperator(operator);
            receipt.setType(1);
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
            //发送钉钉提醒
            Contract contract = contractService.getById(record.getContractId());
            User user = userService.getById(operator);
            WorkRecordVo workRecordVo =
                    new WorkRecordVo("有一份发票待审批", "名称", contract.getName() + receipt.getReceiptName(), user);
            dingdingService.sendWorkRecord(workRecordVo);
        }
        return true;
    }

    @Override
    public List getReceipt(Integer userId) {
        List<Receipt> receiptList = receiptService.list(new QueryWrapper<Receipt>()
                .eq("belong", userId)
                .orderByDesc("id"));
        ArrayList arrayList = new ArrayList();
        for (Receipt r : receiptList) {
            Map<String, Object> map = new HashMap<>();
            map.put("receiptName", r.getReceiptName());
            Record record = recordService.getById(r.getRecordId());
            Contract contract = contractService.getById(record.getContractId());
            map.put("contractName", contract.getName());
            map.put("receiptID", r.getId());
            map.put("receiptType", r.getType());
            map.put("status", r.getFinish());
            arrayList.add(map);
        }
        return arrayList;
    }

    @Override
    public List getApproved(Integer userId) {
        List<ReceiptApprove> approves =
                approveService.list(new QueryWrapper<ReceiptApprove>().eq("approve_people", userId));
        List<Integer> tmp = new ArrayList<>();
        for (ReceiptApprove a : approves) {
            tmp.add(a.getReceiptId());
        }
        List<Receipt> receiptList = receiptService.listByIds(tmp);
        ArrayList arrayList = new ArrayList();
        for (Receipt r : receiptList) {
            Map<String, Object> map = new HashMap<>();
            map.put("receiptName", r.getReceiptName());
            Record record = recordService.getById(r.getRecordId());
            Contract contract = contractService.getById(record.getContractId());
            map.put("contractName", contract.getName());
            map.put("receiptID", r.getId());
            map.put("receiptType", r.getType());
            arrayList.add(map);
        }
        return arrayList;
    }
}
