package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_tag")
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String tagname;
}
