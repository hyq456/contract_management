package com.hdu.contract_management.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author hyq
 * @since 2020-03-27
 */
public class Departments extends Model<Departments> {

    private static final long serialVersionUID=1L;

    /**
     * 部门编号
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String departmentName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Departments{" +
        "id=" + id +
        ", departmentName=" + departmentName +
        "}";
    }
}
