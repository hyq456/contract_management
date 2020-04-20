package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.Contract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.contract_management.utils.ResultUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同表 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-03-22
 */
public interface ContractService extends IService<Contract> {
    public Contract modifyContract(Contract contract);
    public Map<String, Object> getSaleDate(Integer userId);
    public Map<String, Object> getPurchaseDate(Integer userId);
    public Map<String, Object> expire(Integer userId);


}
