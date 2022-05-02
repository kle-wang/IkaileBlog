package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.Article;
import org.apache.ibatis.annotations.Select;


public interface ArticleMapper extends BaseMapper<Article> {
    //    select id from kl_article where friend_name="c-note1";
    @Select("select id from kl_article where friend_name=#{friend_name}")
    Integer getArticleIdByFriendName(String friend_name);
}
