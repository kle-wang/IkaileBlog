package com.ikaileblog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ikaileblog.dao.pojo.Tag;
import com.ikaileblog.vo.TagVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    @Select("select tagname from kl_tag where id in (select tag_id from kl_article_tag where article_id = #{article_id})")
    List<TagVo> findTagNameByArticleId(@Param("article_id") Integer article_id);


}
