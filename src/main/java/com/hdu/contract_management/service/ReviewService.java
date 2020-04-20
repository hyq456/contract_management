package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.Review;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 审核记录 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */
public interface ReviewService extends IService<Review> {
//    public List<Review> getReviewsByContractId(Integer contractId);
public int reviewCount(Integer contractId);

public Boolean increaseReview(Review review);
}
