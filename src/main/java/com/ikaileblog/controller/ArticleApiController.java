package com.ikaileblog.controller;

import com.ikaileblog.service.ArticleService;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.PageParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/post")
public class ArticleApiController {


    @Resource
    ArticleService articleService;

    /**
     * 文章 获取所有文章 分页查询
     **/

    @PostMapping("/article")
    public RestBean<List<ArticleVo>> getAllArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 根据id获取单个文章
     */
    @PostMapping("/article/{id}")
    public RestBean<ArticleVo> getArticleById(@PathVariable Integer id) {
        return articleService.listArticleById(id);
    }

    /**
     * 根据分类的id读分类下的所有文章
     */
    @PostMapping("/category/{CategoryId}")
    public RestBean<List<ArticleVo>> getArticleByCategory(@RequestBody PageParams pageParams, @PathVariable Integer CategoryId) {
        return articleService.listArticleByCategoryId(pageParams, CategoryId);
    }
}
