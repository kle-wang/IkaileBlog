package com.ikaileblog.handler;

import com.ikaileblog.dao.mapper.ArticleMapper;
import com.ikaileblog.dao.pojo.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
@EnableScheduling
public class ViewCountHandler {
    /**
     * redis 浏览量 持久化处理
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private ArticleMapper articleMapper;

    @Scheduled(cron = "0 0 2 * * *") //每天晚上2点持久化
    @Async
    public void scheduled() {
        Map<Object, Object> countMap = redisTemplate.opsForHash().entries("article_view_count");
        for (Map.Entry<Object, Object> entry : countMap.entrySet()) {
            Integer articleId = Integer.valueOf(entry.getKey().toString());
            Integer count = Integer.parseInt(entry.getValue().toString());
            Article article = new Article();
            article.setId(articleId);
            article.setView_count(count);
            articleMapper.updateById(article);
        }
    }

}
