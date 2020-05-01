package com.hdu.contract_management.service.impl;

import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.MailVo;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.service.MailService;
import com.hdu.contract_management.service.UserService;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    UserService userService;
    @Resource
    JavaMailSender javaMailSender;

    public void sendSimpleMail() {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom("544608734@qq.com");
            //邮件接收人
            simpleMailMessage.setTo("544608734@qq.com");
//            simpleMailMessage.setTo(mailVo.getTo());

            //邮件主题
            simpleMailMessage.setSubject("123");

            //邮件内容
            simpleMailMessage.setText("mailBean.getContent()");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.out.println("邮件发送失败"+ e.getMessage());
        }
    }

    @Override
    public void sendHtmlMail() {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("544608734@qq.com");
            helper.setTo("544608734@qq.com");
            helper.setSubject("mailBean.getSubject()");
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>尊敬的XXX</h3>")
                    .append("<p>您有一份合同待审批，请前往查看</p>")
                    .append("<p>合同名：右对齐</p>");
            helper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void newApprove(MailVo mailVo) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("544608734@qq.com");
            helper.setTo(mailVo.getTo());
            helper.setSubject(mailVo.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>尊敬的"+mailVo.getToName()+"</h3>")
                    .append("<p>您有一份合同待审批，请前往系统查看</p>")
                    .append("<p>合同名："+mailVo.getContract().getName()+"</p>")
                    .append("<p>对方公司："+mailVo.getContract().getPartyB()+"</p>")
                    .append("<p>合同金额："+mailVo.getContract().getTotal()+"元</p>");
            helper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newExecute(MailVo mailVo) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("544608734@qq.com");
            helper.setTo(mailVo.getTo());
            helper.setSubject(mailVo.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>尊敬的"+mailVo.getToName()+"</h3>")
                    .append("<p>您有一份合同审批完成，进入执行阶段，请前往系统查看</p>")
                    .append("<p>合同名："+mailVo.getContract().getName()+"</p>")
                    .append("<p>对方公司："+mailVo.getContract().getPartyB()+"</p>")
                    .append("<p>合同金额："+mailVo.getContract().getTotal()+"</p>");
            helper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remind(MailVo mailVo) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("544608734@qq.com");
            helper.setTo(mailVo.getTo());
            helper.setSubject(mailVo.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append("<h3>尊敬的"+mailVo.getToName()+"</h3>")
                    .append("<p>"+mailVo.getText()+"</p>")
                    .append("<p>合同名："+mailVo.getContract().getName()+"</p>")
                    .append("<p>对方公司："+mailVo.getContract().getPartyB()+"</p>")
                    .append("<p>合同金额："+mailVo.getContract().getTotal()+"</p>");
            helper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
