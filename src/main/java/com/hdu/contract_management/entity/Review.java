package com.hdu.contract_management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 审核记录
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */
public class Review extends Model<Review> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 审核合同的id
     */
    private Integer contractId;

    /**
     * 审核部门id
     */
    private Integer departmentId;

    /**
    审核人
     */

    private Integer reviewPeople;
    /**
     * 审核意见
     */
    private String reviewComment;

    /**
     * 是否通过，1：通过 0：未通过
     */
    private Boolean pass;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTime;


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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public LocalDateTime getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(LocalDateTime reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getReviewPeople() {
        return reviewPeople;
    }

    public void setReviewPeople(Integer reviewPeople) {
        this.reviewPeople = reviewPeople;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", contractId=" + contractId +
                ", departmentId=" + departmentId +
                ", reviewPeople=" + reviewPeople +
                ", reviewComment='" + reviewComment + '\'' +
                ", pass=" + pass +
                ", reviewTime=" + reviewTime +
                '}';
    }
}
