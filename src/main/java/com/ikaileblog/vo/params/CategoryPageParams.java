package com.ikaileblog.vo.params;

import lombok.Data;

@Data
public class CategoryPageParams {
    private int page = 1;
    private int pageSize = 10;
    private int categoryId = 1;
}