package com.ikaileblog.controller;

import com.ikaileblog.service.ArticleService;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.PageParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "文章服务", description = "读取文章使用")
@Controller
@ResponseBody
@RequestMapping("/api/post")
public class ArticleApiController {


    @Resource
    ArticleService articleService;

    /**
     * 文章 获取所有文章 分页查询
     **/
    @ApiOperation("获取全部文章 分页")
    @PostMapping("/article")
    public RestBean<List<ArticleVo>> getAllArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 根据id获取单个文章
     */
    @ApiOperation("获取单个文章")
    @PostMapping("/article/{id}")
    public RestBean<ArticleVo> getArticleById(@PathVariable Integer id) {
        return articleService.listArticleById(id);
    }

    /**
     * 根据分类的id读分类下的所有文章
     */
    @ApiOperation("获取分类下信息 分页")
    @PostMapping("/category/{CategoryId}")
    public RestBean<List<ArticleVo>> getArticleByCategory(@RequestBody PageParams pageParams, @PathVariable Integer CategoryId) {
        return articleService.listArticleByCategoryId(pageParams, CategoryId);
    }
}
