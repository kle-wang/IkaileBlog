package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_tag")
public class Tag {
    private Integer id;
    private String tagname;
}
