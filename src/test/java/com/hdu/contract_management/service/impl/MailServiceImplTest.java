package com.hdu.contract_management.service.impl;

import com.hdu.contract_management.entity.MailVo;
import com.hdu.contract_management.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    MailService mailService;
    @Test
    public void aaa(){
        MailVo mailVo = new MailVo();
        mailVo.setFrom("544608734@qq.com");
        mailVo.setTo("544608734@qq.com");
        mailVo.setSubject("TEST");
        mailVo.setToName("hyq");
        mailVo.setText("test.doc");
        mailService.newApprove(mailVo);
    }

}