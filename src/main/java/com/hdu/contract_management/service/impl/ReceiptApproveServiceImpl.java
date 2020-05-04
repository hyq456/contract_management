package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.ReceiptApprove;
import com.hdu.contract_management.entity.Record;
import com.hdu.contract_management.mapper.ReceiptApproveMapper;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.ReceiptApproveService;
import com.hdu.contract_management.service.ReceiptService;
import com.hdu.contract_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 发票审批流程 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-05-03
 */
@Service
public class ReceiptApproveServiceImpl extends ServiceImpl<ReceiptApproveMapper, ReceiptApprove> implements ReceiptApproveService {
    @Autowired
    ReceiptApproveService approveService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ContractService contractService;
    @Autowired
    UserService userService;

    @Override
    public boolean createAndInfo(Receipt receipt) {

        return false;
    }

    @Override
    public List getReceiptApproveList(Integer userId) {
        QueryWrapper<ReceiptApprove> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("approve_people", userId);
        queryWrapper.eq("finish", 0);
        queryWrapper.orderByDesc("id");
        List<ReceiptApprove> approveList = approveService.list(queryWrapper);
        List arrayList = new ArrayList();
        for (ReceiptApprove receiptApprove : approveList) {
            Receipt r = receiptService.getById(receiptApprove.getReceiptId());
            Map<String, Object> map = new HashMap<>();
            map.put("receiptName", r.getReceiptName());
            Contract contract = contractService.getById(r.getContractId());
            map.put("contractName", contract.getName());
            map.put("receiptID", r.getId());
            map.put("receiptType", r.getType());
            map.put("approveId", receiptApprove.getId());
            arrayList.add(map);
        }
        return arrayList;
    }

    @Override
    public void leaderFinish(Integer approveId) {
        ReceiptApprove approve = approveService.getById(approveId);
        approve.setFinish(true);
        approveService.updateById(approve);
        //创建财务部审批
        approve.setFinish(false);
        approve.setId(null);
        Integer operator = userService.getRandomPeople(1, false);
        approve.setApprovePeople(operator);
        approveService.save(approve);
        receiptService.update(new UpdateWrapper<Receipt>().eq("id", approve.getReceiptId())
                .set("operator", operator));

    }

    @Override
    public void financeFinish(Integer approveId, Receipt receipt) {
        ReceiptApprove approve = approveService.getById(approveId);
        approve.setFinish(true);
        approveService.updateById(approve);
        Receipt oldReceipt = receiptService.getById(approve.getReceiptId());
        if (oldReceipt.getReceiptCode() == null) {
            oldReceipt.setReceiptCode(receipt.getReceiptCode());
            oldReceipt.setReceiptNumber(receipt.getReceiptNumber());
            oldReceipt.setReceiptDate(receipt.getReceiptDate());
        }
        oldReceipt.setFinish(true);
        receiptService.updateById(oldReceipt);
        Contract contract = contractService.getById(oldReceipt.getContractId());
        contract.setUnreceipt(contract.getUnreceipt() - oldReceipt.getAmount());
        contractService.updateById(contract);
    }
}
