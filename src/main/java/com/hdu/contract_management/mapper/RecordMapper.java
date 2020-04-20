package com.hdu.contract_management.mapper;

import com.hdu.contract_management.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyq
 * @since 2020-03-30
 */
public interface RecordMapper extends BaseMapper<Record> {
    public LinkedHashMap getMonthSale(@Param("toyear") String toyear, @Param("contractList")List list);
    public LinkedHashMap getMonthPurchase(@Param("toyear") String toyear,@Param("contractList")List list);

}
