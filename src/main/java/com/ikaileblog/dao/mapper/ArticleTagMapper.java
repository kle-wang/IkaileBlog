package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.ArticleTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    @Delete("delete from kl_article_tag where article_id = #{articleId}")
    Boolean deleteArticleTagById(@Param("articleId") Integer articleId);
}
