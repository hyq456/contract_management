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
    int reviewCount(Integer contractId);

    Boolean increaseReview(Review review);

    List<Review> getReview(Integer contractId);
}
