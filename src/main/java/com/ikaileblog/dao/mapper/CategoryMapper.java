package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.Category;
import com.ikaileblog.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select category_name from kl_category where id in (select category_id from kl_article where id = #{articleId})")
    CategoryVo findCategoryByArticleId(@Param("articleId") Integer articleId);


}
