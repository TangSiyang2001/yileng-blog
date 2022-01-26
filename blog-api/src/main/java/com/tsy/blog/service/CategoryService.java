package com.tsy.blog.service;

import com.tsy.blog.web.vo.CategoryVo;
import com.tsy.blog.web.vo.Result;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
public interface CategoryService {
    CategoryVo findCategoryById(Long id);

    Result findAllCategories();
}
