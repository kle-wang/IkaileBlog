package com.ikaileblog.service;

import com.ikaileblog.dao.pojo.Article;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    RestBean<List<ArticleVo>> listArticle(PageParams pageParams);
}
