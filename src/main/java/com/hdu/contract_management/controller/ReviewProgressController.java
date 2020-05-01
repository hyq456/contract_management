package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.hdu.contract_management.entity.ReviewProgress;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.service.ReviewProgressService;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/reviewprogress")
public class ReviewProgressController {
    @Autowired
    ReviewProgressService reviewProgressService;

//    @GetMapping("/{department}")
//    public ResultUtil getReviewProgressByDepartment(@PathVariable(value = "department") Integer department){
//        QueryWrapper<ReviewProgress> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("next_department",department);
//        queryWrapper.eq("done",false);
//        return ResultUtil.success("查询审批进程成功",reviewProgressService.list(queryWrapper));
//    }

    @GetMapping("/")
    public ResultUtil getReviewProgressByDepartment(HttpServletRequest request){
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        QueryWrapper<ReviewProgress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_people",userId);
        queryWrapper.eq("done",0);
        return ResultUtil.success("查询审批进程成功",reviewProgressService.list(queryWrapper));
    }

    @GetMapping("/approved")
    public ResultUtil getMyApprovedReviewProgeree(Integer reviewPeople){
        QueryWrapper<ReviewProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("done",1);
        wrapper.eq("review_people",reviewPeople);
        return ResultUtil.success("查询已审批合同成功",reviewProgressService.list(wrapper));
    }

}

