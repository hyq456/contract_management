package com.hdu.contract_management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 合同表
 * </p>
 *
 * @author hyq
 * @since 2020-05-02
 */
public class Contract extends Model<Contract> {

    private static final long serialVersionUID=1L;

    /**
     * 合同ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同名称
     */
    private String name;

    /**
     * 合同签订部门
     */
    private Integer department;

    /**
     * 签订人
     */
    private Integer signPeople;

    /**
     * 合同类型  0：采购1：销售
     */
    private Integer contractType;

    /**
     * 签订日期
     */
    private LocalDate signDate;

    /**
     * 合同开始日期
     */
    private LocalDate startDate;

    /**
     * 合同截止日期
     */
    private LocalDate stopDate;

    /**
     * 合作状态： 0：被退回 1：审核中 2：执行中 3：已归档 4：已撤销 5：已提前终止
     */
    private Integer contractState;

    /**
     * 乙方名称
     */
    private String partyB;

    /**
     * 总金额
     */
    private Integer total;

    /**
     * 合同描述
     */
    private String contractDescribe;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 待支付金额
     */

    private Integer remainder;

    /**
     * 未开票金额
     */
    private Integer unreceipt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getSignPeople() {
        return signPeople;
    }

    public void setSignPeople(Integer signPeople) {
        this.signPeople = signPeople;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public LocalDate getSignDate() {
        return signDate;
    }

    public void setSignDate(LocalDate signDate) {
        this.signDate = signDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getContractDescribe() {
        return contractDescribe;
    }

    public void setContractDescribe(String contractDescribe) {
        this.contractDescribe = contractDescribe;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public Integer getUnreceipt() {
        return unreceipt;
    }

    public void setUnreceipt(Integer unreceipt) {
        this.unreceipt = unreceipt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", name=" + name +
                ", department=" + department +
                ", signPeople=" + signPeople +
                ", contractType=" + contractType +
                ", signDate=" + signDate +
                ", startDate=" + startDate +
                ", stopDate=" + stopDate +
                ", contractState=" + contractState +
                ", partyB=" + partyB +
                ", total=" + total +
                ", contractDescribe=" + contractDescribe +
                ", filePath=" + filePath +
                ", remainder=" + remainder +
                ", unreceipt=" + unreceipt +
                "}";
    }
}
