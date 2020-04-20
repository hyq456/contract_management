package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.MailVo;

public interface MailService {
    public void sendSimpleMail();
    public void sendHtmlMail();
    public void newApprove(MailVo mailVo);
    public void newExecute(MailVo mailVo);
    public void remind(MailVo mailVo);

}
