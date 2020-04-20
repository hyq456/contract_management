package com.hdu.contract_management.mapper;

import com.hdu.contract_management.entity.Contract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 合同表 Mapper 接口
 * </p>
 *
 * @author hyq
 * @since 2020-03-22
 */
@Component

public interface ContractMapper extends BaseMapper<Contract> {
    public List<Contract> getExpire(@Param("userId")Integer userId);
}
