package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Review;
import com.hdu.contract_management.service.ContractService;
import com.hdu.contract_management.service.ReviewProgressService;
import com.hdu.contract_management.service.ReviewService;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审核记录 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewProgressService reviewProgressService;
    @Autowired
    ContractService contractService;


    @GetMapping("/{id}")
    public ResultUtil getReviewById(@PathVariable(value = "id") Integer id){
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id",id);
        List<Review> list = reviewService.list(queryWrapper);
        if(list!=null){
            return ResultUtil.success("查询成功",list);
        }
        else
            return ResultUtil.error("查询失败");
    }

    @GetMapping({"/getreview/{id}"})
    public ResultUtil getReviewByReviewId(@PathVariable(value = "id")Integer id){
        Review review = reviewService.getById(id);
        return ResultUtil.success("查询审批记录成功",review);
    }

    @PostMapping("/")
    public ResultUtil increaseReview(Review review){

        if(reviewService.increaseReview(review)){
            return ResultUtil.success("保存审批记录成功");
        }
        else
            return ResultUtil.error("保存审批记录失败");

    }

    @GetMapping("/approved")
    public ResultUtil getMyApprovedReview(Integer reviewPeople){
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("review_people",reviewPeople);
        wrapper.orderByDesc("id");
        List<Review> list = reviewService.list(wrapper);
        List result = new ArrayList();

        for (Review review:list) {
            Map<String,Object> map = new HashMap<>();
            map.put("review",review);
            Contract contract = contractService.getById(review.getContractId());
            map.put("contract",contract);

            result.add(map);
        }
        return ResultUtil.success("查询已审批合同成功",result);
    }

    @GetMapping("/approved/{id}")
    public ResultUtil getReviewAndContractById(@PathVariable(value = "id")Integer id){
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        Review review = reviewService.getById(id);
        Contract contract = contractService.getById(review.getContractId());
        Map<String,Object> map = new HashMap<>();
        map.put("review",review);
        map.put("contract",contract);
        return ResultUtil.success("根据审批ID查询审批信息与合同成功",map);
    }

}

