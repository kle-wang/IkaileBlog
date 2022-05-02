package com.ikaileblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikaileblog.dao.mapper.AuthMapper;
import com.ikaileblog.dao.pojo.Account;
import com.ikaileblog.vo.AccountVo;
import com.ikaileblog.vo.RestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(tags = "用户相关", description = "查看用户信息")
@Controller
@ResponseBody

@RequestMapping("/api/user")

public class UserApiController {

    @Resource
    AuthMapper mapper;

    @ApiOperation("获取自己的用户信息")
    @GetMapping("/info-me")
    public RestBean<AccountVo> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Account data = mapper.selectOne(new QueryWrapper<Account>().eq("username", name));
        if (data == null) return new RestBean<>(401, "无法查询到用户信息");
        AccountVo dataVo = new AccountVo();
        BeanUtils.copyProperties(data, dataVo);
        return new RestBean<>(200, "请求成功", dataVo);
    }

}
