package com.ikaileblog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("kl_article")
public class Article {
    /**
     * 文章实体类
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String summary;
    private String create_data;
    private Integer view_count;
    private Integer author_id;
    private Integer body_id;
    private Integer category_id;
    private Integer weight;
    private String title;
    private String friend_name;

}
