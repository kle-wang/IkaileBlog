package com.ikaileblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikaileblog.dao.mapper.*;
import com.ikaileblog.dao.pojo.Account;
import com.ikaileblog.dao.pojo.Article;
import com.ikaileblog.dao.pojo.ArticleBody;
import com.ikaileblog.dao.pojo.ArticleTag;
import com.ikaileblog.service.ArticleService;
import com.ikaileblog.service.ThreadService;
import com.ikaileblog.vo.ArticleVo;
import com.ikaileblog.vo.CategoryVo;
import com.ikaileblog.vo.RestBean;
import com.ikaileblog.vo.TagVo;
import com.ikaileblog.vo.params.ArticleParam;
import com.ikaileblog.vo.params.CategoryPageParams;
import com.ikaileblog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    ArticleTagMapper articleTagMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    ThreadService threadService;
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 1.分页查询article数据表 所有文章
     */
    @Override
    public RestBean<List<ArticleVo>> listArticle(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreate_data);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return new RestBean<>(200, "请求成功", articleVoList);
    }

    /**
     * 根据id查找文章
     */
    @Override
    public RestBean<ArticleVo> listArticleById(Integer id) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Article article = articleMapper.selectOne(queryWrapper);
        if (article == null) return new RestBean<>(404, "文章不存在");
        //开始处理浏览量
        ArticleVo articleVo = copy(article);
        threadService.updateArticleViewCount(article);
        String viewCount = (String) redisTemplate.opsForHash().get("article_view_count", String.valueOf(id));
        if (viewCount != null) {
            articleVo.setView_count(Integer.parseInt(viewCount));
        }
        return new RestBean<>(200, "请求成功", articleVo);
    }


    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        Integer articleId = article.getId();
        articleVo.setTags(tagMapper.findTagNameByArticleId(articleId));
        articleVo.setBody(articleBodyMapper.findArticleBodyById(articleId));
        articleVo.setAuthor(authMapper.getAuthorByArticleId(articleId));
        articleVo.setCategory(categoryMapper.findCategoryByArticleId(articleId));
        return articleVo;
    }


    /**
     * 分类下所有文章
     */
    @Override
    public RestBean<List<ArticleVo>> listArticleByCategoryId(CategoryPageParams categoryPageParams) {
        Page<Article> page = new Page<>(categoryPageParams.getPage(), categoryPageParams.getPageSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryPageParams.getCategoryId()).orderByDesc("weight", "create_data");
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records);
        return new RestBean<>(200, "请求成功", articleVoList);
    }


    /**
     * 文章发布
     */
    @Override
    public RestBean<Integer> publish(ArticleParam articleParam) {
        /*
         * 1、作者id
         * 2、文章标题
         * 3、文章内容
         * 4、文章分类
         * 5、文章标签
         */
        //用户判断
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Account data = authMapper.selectOne(new QueryWrapper<Account>().eq("username", name));
        //获取值
        Integer id = data.getId();
        String title = articleParam.getTitle();
        String context = articleParam.getBody().getContext();
        CategoryVo category = articleParam.getCategory();
        List<TagVo> tags = articleParam.getTags();
        //基本内容
        Article article = new Article();
        article.setAuthor_id(id);
        article.setTitle(title);
        article.setCategory_id(category.getId());
        long currentTime = System.currentTimeMillis();
        //2021-07-23 11:34:20
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime);
        String currentDate = simpleDateFormat.format(date);
        article.setCreate_data(currentDate);
        this.articleMapper.insert(article);
        //赋值Tags
        if (tags != null) {
            for (TagVo tag : tags) {
                Integer articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticle_id(articleId);
                articleTag.setTag_id(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        //文章body
        //先来文章body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticle_id(article.getId());
        articleBody.setContext(context);
        articleBodyMapper.insert(articleBody);
        //写到article里
        article.setBody_id(articleBody.getId());
        //summary
        article.setSummary(context.substring(0, 30));
        articleMapper.updateById(article);
        //返回结果咯
        return new RestBean<>(200, "加入成功", article.getId());

    }

    @Override
    public RestBean<Void> delete(Integer id) {
        // article本身
        // articleBody
        // article_tag
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Article article = articleMapper.selectOne(queryWrapper);
        if (article == null) return new RestBean<>(404, "文章不存在");
        articleMapper.deleteById(id);
        articleBodyMapper.deleteArticleBodyById(id);
        articleTagMapper.deleteArticleTagById(id);
        return new RestBean<>(200, "删除完毕");
    }


}

