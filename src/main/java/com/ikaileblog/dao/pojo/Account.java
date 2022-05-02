package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_accounts")
public class Account {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String role;

}
