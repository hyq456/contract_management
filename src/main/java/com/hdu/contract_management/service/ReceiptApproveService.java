package com.hdu.contract_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.entity.ReceiptApprove;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 发票审批流程 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-05-03
 */
public interface ReceiptApproveService extends IService<ReceiptApprove> {
    boolean createAndInfo(Receipt receipt);
}
