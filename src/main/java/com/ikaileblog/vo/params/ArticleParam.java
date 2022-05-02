package com.ikaileblog.vo.params;

import com.ikaileblog.vo.CategoryVo;
import com.ikaileblog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private Integer id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private List<TagVo> tags;
    private String summary;
    private String title;
}
