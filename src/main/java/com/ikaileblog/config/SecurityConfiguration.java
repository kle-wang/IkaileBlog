package com.ikaileblog.config;

import com.ikaileblog.cache.RedisTokenRepository;
import com.ikaileblog.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    RedisTokenRepository redisTokenRepository;
    @Resource
    AuthServiceImpl service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/auth/**", "/api/post/**", "/api/category").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/api/auth/access-deny")
                .loginProcessingUrl("/api/auth/login")
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/api/auth/logout-success")
                .and()
                .rememberMe()
                .userDetailsService(service)
                .tokenRepository(redisTokenRepository)
                .rememberMeParameter("remember")
                .and()
                .csrf()
                .disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }






}