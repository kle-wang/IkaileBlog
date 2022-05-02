package com.ikaileblog.controller;

import com.ikaileblog.service.AuthVerifyService;
import com.ikaileblog.service.impl.AuthServiceImpl;
import com.ikaileblog.vo.RestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Api(tags = "验证功能", description = "关于注册、登录方面")
@Controller
@ResponseBody
@RequestMapping("/api/auth")

public class AuthApiController {

    @Resource
    AuthServiceImpl authService;

    @Resource
    AuthVerifyService authVerifyService;

    @ApiOperation("请求邮件验证码")
    @GetMapping("/verify-code")
    public RestBean<Void> verifyCode(@RequestParam("email") String email) {
        authVerifyService.SendVerifyCode(email);
        return new RestBean<>(200,"发送成功");
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public RestBean<Void> register (String username,String password,String email,String verify){
        if(authVerifyService.doVerify(email,verify)){
            authService.creatAccount(username,password,"user");
            return new RestBean<>(200,"注册成功");
        }else{
            return new RestBean<>(403,"注册失败，验证码错误");
        }
    }

    @ApiIgnore
    @RequestMapping("/login-success")
    public RestBean<Void> loginSuccess(){
        return new RestBean<>(200,"登录成功");
    }

    @ApiIgnore
    @RequestMapping("/login-failure")
    public RestBean<Void> loginFailure(){
        return new RestBean<>(403,"登录失败，账号密码不匹配");
    }

    @ApiIgnore
    @RequestMapping("/logout-success")
    public RestBean<Void> logOutSuccess(){
        return new RestBean<>(200,"再见");
    }

    @ApiIgnore
    @RequestMapping("/access-deny")
    public RestBean<Void> accessDeny(){
        return new RestBean<>(403,"非法请求，请登录后重试");
    }

}
