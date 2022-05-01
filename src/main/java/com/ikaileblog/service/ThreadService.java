package com.ikaileblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ikaileblog.dao.mapper.ArticleMapper;
import com.ikaileblog.dao.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class ThreadService {
    @Resource
    ArticleMapper articleMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 初始化redis 读取文章的浏览量，如果没有，从数据库里读初始化
     */
    @PostConstruct
    public void initViewCount() {
        List<Article> articles = articleMapper.selectList(new QueryWrapper<>());
        for (Article article : articles) {
            String viewCountStr = (String) redisTemplate.opsForHash().get("article_view_count", String.valueOf(article.getId()));
            if (viewCountStr == null) {
                //没有数据，从数据库里面拿
                redisTemplate.opsForHash().put("article_view_count", String.valueOf(article.getId()), String.valueOf(article.getView_count()));
            }
        }
    }

    /**
     * 异步处理
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(Article article) {
        redisTemplate.opsForHash().increment("article_view_count", String.valueOf(article.getId()), 1);
    }

}
