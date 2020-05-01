package com.hdu.contract_management.entity.vo;

import com.hdu.contract_management.entity.User;

public class WorkRecordVo {
    private String title;
    private String subTitle;
    private String content;
    private User user;

    public WorkRecordVo(String title, String subTitle, String content, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
