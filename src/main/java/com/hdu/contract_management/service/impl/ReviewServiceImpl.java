package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.*;
import com.hdu.contract_management.entity.vo.WorkRecordVo;
import com.hdu.contract_management.mapper.ReviewMapper;
import com.hdu.contract_management.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.utils.ResultUtil;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 审核记录 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewProgressService reviewProgressService;
    @Autowired
    ContractService contractService;
    @Autowired
    ContractChangeService contractChangeService;
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    DingdingService dingdingService;

    @Override
    public int reviewCount(Integer contractId) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id", contractId);
        return reviewService.count(queryWrapper);
    }

    @Override
    public Boolean increaseReview(Review review) {
        QueryWrapper<ReviewProgress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_id", review.getContractId());
        queryWrapper.orderByDesc("id");
        queryWrapper.isNotNull("review_id");
        queryWrapper.last("limit 1");
        ReviewProgress last = reviewProgressService.getOne(queryWrapper);
        if (last != null) {
            Review lastReview = reviewService.getById(last.getReviewId());
            if (!lastReview.getPass()) {
                review.setId(lastReview.getId());
            }
        }

        review.setReviewTime(LocalDateTime.now());
        reviewService.saveOrUpdate(review);
        QueryWrapper<Review> wrapper = new QueryWrapper();
        wrapper.eq("contract_id", review.getContractId());
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        review = reviewService.getOne(wrapper);
        queryWrapper.clear();
        queryWrapper.eq("contract_id", review.getContractId());
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");

        ReviewProgress reviewProgress = reviewProgressService.getOne(queryWrapper);
        reviewProgress.setDone(true);
        reviewProgress.setReviewPeople(review.getReviewPeople());
        reviewProgress.setReviewId(review.getId());
        reviewProgressService.updateById(reviewProgress);

        if (review.getPass()) {
            //当前审批部门为法务部
            if (reviewProgress.getNextDepartment() == 1) {
                reviewProgress.setId(null);
                reviewProgress.setDone(false);
                reviewProgress.setNextDepartment(2);
                reviewProgress.setSubTime(LocalDateTime.now());
                //给财务部发邮件
                Contract contract = contractService.getById(reviewProgress.getContractId());
                List<User> userList = userService.list(
                        new QueryWrapper<User>().eq("department", 2));
                Random random = new Random();
                int n = random.nextInt(userList.size());
                User user = userList.get(n);
                MailVo mailVo = new MailVo();
                mailVo.setTo(user.getEmail());
                mailVo.setToName(user.getRealname());
                mailVo.setSubject("新合同审批提醒");
                mailVo.setContract(contract);
                mailService.newApprove(mailVo);
                //发送钉钉提醒
                WorkRecordVo workRecordVo = new WorkRecordVo("您有一份合同待审批","合同名",contract.getName(),user);
                dingdingService.sendWorkRecord(workRecordVo);

                reviewProgress.setReviewPeople(user.getId());
                reviewProgressService.save(reviewProgress);

                return true;
            }
            //当前审批部门为财务部,即审批流程完成
            else if (reviewProgress.getNextDepartment() == 2) {
                //新建合同
                if (reviewProgress.getType() == 1) {
                    Contract contract = contractService.getById(review.getContractId());
                    contract.setContractState(2);
                    contractService.updateById(contract);
                    //给业务员发邮件
                    User user = userService.getById(contract.getSignPeople());
                    MailVo mailVo = new MailVo();
                    mailVo.setTo(user.getEmail());
                    mailVo.setToName(user.getRealname());
                    mailVo.setSubject("合同审批完成提醒");
                    mailVo.setContract(contract);
                    mailService.newExecute(mailVo);
                    //发送钉钉提醒
                    WorkRecordVo workRecordVo = new WorkRecordVo("您有一份合同审批完毕，进入执行","合同名",contract.getName(),user);
                    dingdingService.sendWorkRecord(workRecordVo);
                    return true;
                }
                //变更合同
                else if (reviewProgress.getType() == 2) {
                    Contract contract = contractService.getById(review.getContractId());
                    ContractChange contractChange = contractChangeService.getOne(new QueryWrapper<ContractChange>().eq("contract_id", review.getContractId()));
                    contract.setName(contractChange.getName());
                    contract.setTotal(contractChange.getTotal());
                    contract.setStartDate(contractChange.getStartDate());
                    contract.setStopDate(contractChange.getStopDate());
                    contract.setContractDescribe(contractChange.getContractDescribe());
                    contract.setFilePath(contractChange.getFilePath());
                    contractService.saveOrUpdate(contract);
                    //发邮件
                    User user = userService.getById(contract.getSignPeople());
                    MailVo mailVo = new MailVo();
                    mailVo.setTo(user.getEmail());
                    mailVo.setToName(user.getRealname());
                    mailVo.setSubject("合同变更审批完成提醒");
                    mailVo.setContract(contract);
                    mailService.newExecute(mailVo);
                    //发送钉钉提醒
                    WorkRecordVo workRecordVo = new WorkRecordVo("您有一份合同审批完毕，进入执行","合同名",contract.getName(),user);
                    dingdingService.sendWorkRecord(workRecordVo);
                    return true;
                }
//                合同终止
                else if (reviewProgress.getType() == 3) {
                    Contract contract = contractService.getById(review.getContractId());
                    contract.setContractState(5);
                    contractService.updateById(contract);
                    //发邮件
                    User user = userService.getById(contract.getSignPeople());
                    MailVo mailVo = new MailVo();
                    mailVo.setTo(user.getEmail());
                    mailVo.setToName(user.getRealname());
                    mailVo.setSubject("合同变更审批完成提醒");
                    mailVo.setContract(contract);
                    mailVo.setText("您有一份合同审批完成，已被终止");
                    mailService.remind(mailVo);
                    //发送钉钉提醒
                    WorkRecordVo workRecordVo = new WorkRecordVo("您有一份合同审批完成，已被终止","合同名",contract.getName(),user);
                    dingdingService.sendWorkRecord(workRecordVo);
                    return true;
                }
                return false;

            }
            //当前审批部门为业务员所在部门
            else {
                reviewProgress.setId(null);
                reviewProgress.setDone(false);
                //送往法务部审批
                reviewProgress.setNextDepartment(1);
                reviewProgress.setSubTime(LocalDateTime.now());
                //给法务部发邮件
                Contract contract = contractService.getById(reviewProgress.getContractId());
                List<User> userList = userService.list(
                        new QueryWrapper<User>().eq("department", 1));
                Random random = new Random();
                int n = random.nextInt(userList.size());
                User user = userList.get(n);
                MailVo mailVo = new MailVo();
                mailVo.setTo(user.getEmail());
                mailVo.setToName(user.getRealname());
                mailVo.setSubject("新合同审批提醒");
                mailVo.setContract(contract);
                mailService.newApprove(mailVo);
                //发送钉钉提醒
                WorkRecordVo workRecordVo = new WorkRecordVo("您有一份合同待审批","合同名",contract.getName(),user);
                dingdingService.sendWorkRecord(workRecordVo);

                reviewProgress.setReviewPeople(user.getId());
                reviewProgressService.save(reviewProgress);

                return true;
            }
        }
        //若是审批未通过
        else {
            Contract contract = contractService.getById(review.getContractId());
            contract.setContractState(0);
            contractService.updateById(contract);
            //发邮件
            User user = userService.getById(contract.getSignPeople());
            MailVo mailVo = new MailVo();
            mailVo.setTo(user.getEmail());
            mailVo.setToName(user.getRealname());
            mailVo.setSubject("合同审批被退回提醒");
            mailVo.setContract(contract);
            mailVo.setText("您有一份合同审批被退回，原因是：" + review.getReviewComment());
            mailService.remind(mailVo);
            //发送钉钉提醒
            WorkRecordVo workRecordVo = new WorkRecordVo("您有一份审批被退回", "合同名", contract.getName(), user);
            dingdingService.sendWorkRecord(workRecordVo);
            return true;
        }
    }

    @Override
    public List<Review> getReview(Integer contractId) {
        QueryWrapper<ReviewProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("contract_id", contractId);
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");

        Integer type = reviewProgressService.getOne(wrapper).getType();
        wrapper.clear();
        wrapper.eq("contract_id", contractId);
        wrapper.eq("type", type);
        List<ReviewProgress> list1 = reviewProgressService.list(wrapper);
        List<Integer> ids = new ArrayList<>();
        for (ReviewProgress r : list1) {
            ids.add(r.getReviewId());
        }
        return reviewService.listByIds(ids);
    }
}
