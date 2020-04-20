package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.ReviewProgress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyq
 * @since 2020-04-01
 */
public interface ReviewProgressService extends IService<ReviewProgress> {
    public void increasePregressByContract(Contract contract);

    public void increasePregressByModifyContract(Contract contract);

    public void increaseProgressByChangeContract(Contract contract);

    public void increaseProgressByStopContract(Contract contract);

    public int waitApproveCount(Integer id);
}
