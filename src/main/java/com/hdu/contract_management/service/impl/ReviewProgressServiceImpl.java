package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.MailVo;
import com.hdu.contract_management.entity.ReviewProgress;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.mapper.ReviewProgressMapper;
import com.hdu.contract_management.service.MailService;
import com.hdu.contract_management.service.ReviewProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-04-01
 */
@Service
public class ReviewProgressServiceImpl extends ServiceImpl<ReviewProgressMapper, ReviewProgress> implements ReviewProgressService {
    @Autowired
    ReviewProgressService reviewProgressService;
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

    @Override
    public void increasePregressByContract(Contract contract) {
        ReviewProgress reviewProgress = new ReviewProgress();
        reviewProgress.setSubTime(LocalDateTime.now());
        reviewProgress.setNextDepartment(contract.getDepartment());
        reviewProgress.setContractName(contract.getName());
        reviewProgress.setPartyB(contract.getPartyB());
        reviewProgress.setDone(false);
        reviewProgress.setContractId(contract.getId());
        reviewProgress.setType(1);

        //给分配审批人员并发邮件
        List<User> userList = userService.list(
                new QueryWrapper<User>().eq("department", contract.getDepartment())
                        .eq("role", 2));
        Random random = new Random();
        int n = random.nextInt(userList.size());
        User user = userList.get(n);
        MailVo mailVo = new MailVo();
        mailVo.setTo(user.getEmail());
        mailVo.setToName(user.getRealname());
        mailVo.setSubject("新合同审批提醒");
        mailVo.setContract(contract);
        mailService.newApprove(mailVo);
        reviewProgress.setReviewPeople(user.getId());
        reviewProgressService.save(reviewProgress);

    }

    @Override
    public void increasePregressByModifyContract(Contract contract) {
        ReviewProgress reviewProgress = new ReviewProgress();
        reviewProgress.setSubTime(LocalDateTime.now());
        reviewProgress.setContractName(contract.getName());
        reviewProgress.setPartyB(contract.getPartyB());
        reviewProgress.setDone(false);
        reviewProgress.setContractId(contract.getId());
        //找到上一次被哪个部门退回
        QueryWrapper<ReviewProgress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id",contract.getId());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("LIMIT 1");
        ReviewProgress lastone = reviewProgressService.getOne(queryWrapper);
        reviewProgress.setNextDepartment(lastone.getNextDepartment());
        reviewProgress.setType(lastone.getType());
        List<User> userList = userService.list(
                new QueryWrapper<User>().eq("department", lastone.getNextDepartment())
                        .eq("role", 2));
        Random random = new Random();
        int n = random.nextInt(userList.size());
        User user = userList.get(n);
        MailVo mailVo = new MailVo();
        mailVo.setTo(user.getEmail());
        mailVo.setToName(user.getRealname());
        mailVo.setSubject("新合同审批提醒");
        mailVo.setContract(contract);
        mailService.newApprove(mailVo);
        reviewProgress.setReviewPeople(user.getId());
        reviewProgressService.save(reviewProgress);

    }

    @Override
    public void increaseProgressByChangeContract(Contract contract) {
        ReviewProgress reviewProgress = new ReviewProgress();
        reviewProgress.setSubTime(LocalDateTime.now());
        reviewProgress.setNextDepartment(contract.getDepartment());
        reviewProgress.setContractName(contract.getName());
        reviewProgress.setPartyB(contract.getPartyB());
        reviewProgress.setDone(false);
        reviewProgress.setContractId(contract.getId());
        reviewProgress.setType(2);
        List<User> userList = userService.list(
                new QueryWrapper<User>().eq("department", contract.getDepartment())
                        .eq("role", 2));
        Random random = new Random();
        int n = random.nextInt(userList.size());
        User user = userList.get(n);
        MailVo mailVo = new MailVo();
        mailVo.setTo(user.getEmail());
        mailVo.setToName(user.getRealname());
        mailVo.setSubject("新合同审批提醒");
        mailVo.setContract(contract);
        mailService.newApprove(mailVo);
        reviewProgress.setReviewPeople(user.getId());
        reviewProgressService.save(reviewProgress);

    }

    @Override
    public void increaseProgressByStopContract(Contract contract) {
        ReviewProgress reviewProgress = new ReviewProgress();
        reviewProgress.setSubTime(LocalDateTime.now());
        reviewProgress.setNextDepartment(contract.getDepartment());
        reviewProgress.setContractName(contract.getName());
        reviewProgress.setPartyB(contract.getPartyB());
        reviewProgress.setDone(false);
        reviewProgress.setContractId(contract.getId());
        reviewProgress.setType(3);
        List<User> userList = userService.list(
                new QueryWrapper<User>().eq("department", contract.getDepartment())
                        .eq("role", 2));
        Random random = new Random();
        int n = random.nextInt(userList.size());
        User user = userList.get(n);
        MailVo mailVo = new MailVo();
        mailVo.setTo(user.getEmail());
        mailVo.setToName(user.getRealname());
        mailVo.setSubject("新合同审批提醒");
        mailVo.setContract(contract);
        mailService.newApprove(mailVo);
        reviewProgress.setReviewPeople(user.getId());
        reviewProgressService.save(reviewProgress);

    }

    @Override
    public int waitApproveCount(Integer id) {
        return reviewProgressService.count(new QueryWrapper<ReviewProgress>().eq("review_people", id).eq("done",0));
    }
}
