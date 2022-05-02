package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.ArticleBody;
import com.ikaileblog.vo.ArticleBodyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {

    @Select("select * from kl_article_body where article_id in (select body_id from kl_article where id = #{articleId})")
    ArticleBodyVo findArticleBodyById(@Param("articleId") Integer articleId);

}
