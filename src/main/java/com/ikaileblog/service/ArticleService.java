package com.ikaileblog.service;

import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.ArticleParam;
import com.ikaileblog.vo.params.CategoryPageParams;
import com.ikaileblog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    /**
     * 分页查询文章列表
     */
    RestBean<List<ArticleVo>> listArticle(PageParams pageParams);

    RestBean<ArticleVo> listArticleById(Integer id);

    RestBean<ArticleVo> listArticleByFriendName(String friend_name);

    RestBean<List<ArticleVo>> listArticleByCategoryId(CategoryPageParams categoryPageParams);

    RestBean<Integer> publish(ArticleParam articleParam);

    RestBean<Void> delete(Integer id);
}
