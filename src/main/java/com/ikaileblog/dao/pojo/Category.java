package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_category")
public class Category {
    private Integer id;
    private String category_name;
}
