package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.ReceiptApprove;
import com.hdu.contract_management.mapper.ReceiptApproveMapper;
import com.hdu.contract_management.service.ReceiptApproveService;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean createAndInfo(Receipt receipt) {

        return false;
    }
}
