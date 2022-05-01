package com.ikaileblog.service;

import org.apache.ibatis.annotations.Mapper;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthVerifyService {
    void SendVerifyCode(String mail);
    Boolean doVerify(String mail,String code);
}
