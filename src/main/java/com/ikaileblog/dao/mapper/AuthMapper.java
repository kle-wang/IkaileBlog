package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.Account;
import com.ikaileblog.vo.AccountVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


//用户mapper

public interface AuthMapper extends BaseMapper<Account> {

    @Select("select * from kl_accounts where id in (select author_id from kl_article where id = #{articleId})")
    AccountVo getAuthorByArticleId(@Param("articleId") Integer articleId);
}
