package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("kl_article_tag")
public class ArticleTag {
    private Integer id;
    private Integer article_id;
    private Integer tag_id;
}
