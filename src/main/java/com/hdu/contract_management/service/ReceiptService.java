package com.hdu.contract_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.contract_management.entity.Receipt;

import java.util.List;

/**
 * <p>
 * 发票 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-05-01
 */
public interface ReceiptService extends IService<Receipt> {
    boolean createReceipt(Receipt receipt);

    List getReceipt(Integer userId);
}
