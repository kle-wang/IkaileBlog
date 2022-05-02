package com.ikaileblog.controller;

import com.ikaileblog.service.ArticleService;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.ArticleParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(tags = "管理员API", description = "管理员权限访问")
@Controller
@ResponseBody
@RequestMapping("/api/admin")
public class ArticleAdminApiController {

    @Resource
    ArticleService articleService;

    /**
     * 发表文章
     */
    @ApiOperation("发布文章")
    @PostMapping("/publish")
    public RestBean<Integer> publishArticle(@RequestBody ArticleParam articleParam) {
        if (articleParam == null) return new RestBean<>(403, "参数错误");
        return articleService.publish(articleParam);
    }

    /**
     * 删除文章
     */
    @PostMapping("/delete")
    public RestBean<Void> deleteArticle(@Param("id") Integer id) {
        if (id == null) return new RestBean<>(403, "参数错误");
        return articleService.delete(id);
    }

}
