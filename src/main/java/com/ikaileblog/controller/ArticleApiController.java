package com.ikaileblog.controller;

import com.ikaileblog.dao.pojo.Article;
import com.ikaileblog.service.ArticleService;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.dao.mapper.ArticleMapper;
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
     * 文章 分页查询
     * **/
    @PostMapping("/article")
    public RestBean<List<ArticleVo>> getAllArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
     //   List<Article> articles = mapper.selectList(null);
       // return new RestBean<>(200, "请求成功", articles);
    }

}
