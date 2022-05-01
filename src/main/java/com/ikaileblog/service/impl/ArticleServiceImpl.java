package com.ikaileblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ikaileblog.cache.RedisMybatisCache;
import com.ikaileblog.dao.mapper.*;
import com.ikaileblog.dao.pojo.Article;
import com.ikaileblog.service.ArticleService;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleMapper articleMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    ArticleBodyMapper articleBodyMapper;
    @Resource
    AuthMapper authMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Override
    public RestBean<List<ArticleVo>> listArticle(PageParams pageParams) {
        /**
         * 1.分页查询article数据表
         */
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreate_data);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return new RestBean<>(200, "请求成功", articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        Integer articleId = article.getId();
        articleVo.setTags(tagMapper.findTagNameByArticleId(articleId));
        articleVo.setBody(articleBodyMapper.findArticleBodyById(articleId));
        articleVo.setAuthor(authMapper.getAuthorByArticleId(articleId));
        articleVo.setCategory(categoryMapper.findCategoryByArticleId(articleId));
        return articleVo;
    }
}
