package com.ikaileblog.controller;

import com.ikaileblog.dao.mapper.CategoryMapper;
import com.ikaileblog.vo.CategoryVo;
import com.ikaileblog.vo.RestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api")
public class CategoryApiController {

    @Resource
    CategoryMapper categoryMapper;

    @GetMapping("/category")
    public RestBean<List<CategoryVo>> getAllCategory() {
        List<CategoryVo> categoryVoList = categoryMapper.findAll();
        if (categoryVoList == null) return new RestBean<>(404, "无分类列表");
        return new RestBean<>(200, "请求成功", categoryVoList);
    }
}
