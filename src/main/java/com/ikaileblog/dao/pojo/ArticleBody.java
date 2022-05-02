package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_article_body")
public class ArticleBody {
    private Integer id;
    private String context;
    private Integer article_id;
}
