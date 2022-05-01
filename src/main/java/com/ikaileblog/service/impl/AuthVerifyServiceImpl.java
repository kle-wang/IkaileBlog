package com.ikaileblog.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.InternetAddressEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthVerifyServiceImpl implements com.ikaileblog.service.AuthVerifyService {

    @Resource
    JavaMailSender sender;
    @Resource
    StringRedisTemplate template;
    @Value("${spring.mail.username}")
    String mailFrom;

    @Override
    public void SendVerifyCode(String mail){
        Random random = new Random();
        int code = random.nextInt(899999)+100000;
        template.opsForValue().set("VerifyCode:"+mail,code+"",3, TimeUnit.MINUTES);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("[凯乐窝]邮件验证");
        message.setText("欢迎您注册，您的验证码是：" +code+",请及时验证，三分钟后验证码将失效。");
        message.setTo(mail);
        message.setFrom("凯乐窝"+'<'+mailFrom+'>');
        sender.send(message);
    }

    @Override
    public Boolean doVerify(String mail, String code) {
        String string = template.opsForValue().get("VerifyCode:"+mail);
        if(string == null) return false;
        if(!code.equals(string)) return false;
        template.delete("VerifyCode:"+mail);
        return true;
    }
}
