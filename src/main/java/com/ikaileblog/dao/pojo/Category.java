package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String category_name;
}
