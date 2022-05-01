package com.ikaileblog.controller;

import com.ikaileblog.dao.mapper.ArticleMapper;
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
    ArticleMapper mapper;

    @Resource
    ArticleService articleService;

    /**
     * 文章 获取所有文章 分页查询
     **/
    @PostMapping("/article")
    public RestBean<List<ArticleVo>> getAllArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    @PostMapping("/article/{id}")
    public RestBean<ArticleVo> getArticleById(@PathVariable Integer id) {
        return articleService.listArticleById(id);
    }

}
