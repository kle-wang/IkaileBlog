package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_article_body")
public class ArticleBody {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String context;
    private Integer article_id;
}
