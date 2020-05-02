package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.entity.Receipt;
import com.hdu.contract_management.mapper.ReceiptMapper;
import com.hdu.contract_management.service.ReceiptService;
import org.springframework.stereotype.Service;

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

}
