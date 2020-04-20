package com.hdu.contract_management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyq
 * @since 2020-04-07
 */
public class ReviewProgress extends Model<ReviewProgress> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer contractId;

    private Integer nextDepartment;

    /**
     * 提交日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime subTime;

    private String contractName;

    private String partyB;

    /**
     * 0:未处理，1：已处理
     */
    private Boolean done;

    private Integer reviewPeople;

    /**
     * 1:新增合同 2：合同变更 3：提前终止
     */
    private Integer type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getNextDepartment() {
        return nextDepartment;
    }

    public void setNextDepartment(Integer nextDepartment) {
        this.nextDepartment = nextDepartment;
    }

    public LocalDateTime getSubTime() {
        return subTime;
    }

    public void setSubTime(LocalDateTime subTime) {
        this.subTime = subTime;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getReviewPeople() {
        return reviewPeople;
    }

    public void setReviewPeople(Integer reviewPeople) {
        this.reviewPeople = reviewPeople;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReviewProgress{" +
        "id=" + id +
        ", contractId=" + contractId +
        ", nextDepartment=" + nextDepartment +
        ", subTime=" + subTime +
        ", contractName=" + contractName +
        ", partyB=" + partyB +
        ", done=" + done +
        ", reviewPeople=" + reviewPeople +
        ", type=" + type +
        "}";
    }
}
