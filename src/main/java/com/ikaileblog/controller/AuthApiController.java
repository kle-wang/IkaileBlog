package com.ikaileblog.controller;

import com.ikaileblog.vo.RestBean;
import com.ikaileblog.service.AuthVerifyService;
import com.ikaileblog.service.impl.AuthServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/api/auth")
public class AuthApiController {

    @Resource
    AuthServiceImpl authService;

    @Resource
    AuthVerifyService authVerifyService;

    @GetMapping("/verify-code")
    public RestBean<Void> verifyCode(@RequestParam("email") String email) {
        authVerifyService.SendVerifyCode(email);
        return new RestBean<>(200,"发送成功");
    }

    @PostMapping("/register")
    public RestBean<Void> register (String username,String password,String email,String verify){
        if(authVerifyService.doVerify(email,verify)){
            authService.creatAccount(username,password,"user");
            return new RestBean<>(200,"注册成功");
        }else{
            return new RestBean<>(403,"注册失败，验证码错误");
        }
    }

    @RequestMapping("/login-success")
    public RestBean<Void> loginSuccess(){
        return new RestBean<>(200,"登录成功");
    }

    @RequestMapping("/login-failure")
    public RestBean<Void> loginFailure(){
        return new RestBean<>(403,"登录失败，账号密码不匹配");
    }

    @RequestMapping("/logout-success")
    public RestBean<Void> logOutSuccess(){
        return new RestBean<>(200,"再见");
    }

    @RequestMapping("/access-deny")
    public RestBean<Void> accessDeny(){
        return new RestBean<>(403,"非法请求，请登录后重试");
    }

}
