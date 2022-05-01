package com.ikaileblog.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Integer id;
    private String summary;
    private String create_data;
    private Integer view_count;
    private Integer weight;
    private String title;
    private AccountVo author;
    private ArticleBodyVo body;
    private List<TagVo> tags;
    private CategoryVo category;
}
