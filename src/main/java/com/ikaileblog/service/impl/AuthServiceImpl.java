package com.ikaileblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikaileblog.dao.mapper.AuthMapper;
import com.ikaileblog.dao.pojo.Account;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//用户认证服务
@Service
public class AuthServiceImpl implements UserDetailsService {

    @Resource
    AuthMapper mapper;

    /**
     * 登录
     * **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account data = mapper.selectOne(new QueryWrapper<Account>().eq("username",username));
        if (data == null) throw new UsernameNotFoundException("用户不存在");
        return User
                .withUsername(data.getUsername())
                .password(data.getPassword())
                .roles(data.getRole())
                .build();
    }
    /**
     * 注册
     * **/
    public void creatAccount(String username,String password,String role){
        Account account = new Account();
        String encodePassword = new BCryptPasswordEncoder().encode(password);
        account.setUsername(username);
        account.setPassword(encodePassword);
        account.setRole(role);
        mapper.insert(account);
    }
}
