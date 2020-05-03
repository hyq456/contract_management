package com.hdu.contract_management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 发票审批流程
 * </p>
 *
 * @author hyq
 * @since 2020-05-03
 */
public class ReceiptApprove extends Model<ReceiptApprove> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应发票ID
     */
    private Integer receiptId;

    /**
     * 审批人
     */
    private Integer approvePeople;

    /**
     * 0：未完成 1：已完成
     */
    private Boolean finish;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getApprovePeople() {
        return approvePeople;
    }

    public void setApprovePeople(Integer approvePeople) {
        this.approvePeople = approvePeople;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReceiptApprove{" +
                "id=" + id +
                ", receiptId=" + receiptId +
                ", approvePeople=" + approvePeople +
                ", finish=" + finish +
                "}";
    }
}
