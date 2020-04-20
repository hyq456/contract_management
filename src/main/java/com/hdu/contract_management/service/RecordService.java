package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyq
 * @since 2020-03-30
 */
public interface RecordService extends IService<Record> {
    public List<Record> getRecordsbyContract(Integer contractId);
    public LinkedHashMap getMonthSale(String toyear,Integer id );
    public LinkedHashMap getMonthPurchase(String toyear,Integer id);


}
