package com.tsy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsy.blog.dao.mapper.CategoryMapper;
import com.tsy.blog.dao.dto.Category;
import com.tsy.blog.service.CategoryService;
import com.tsy.blog.web.vo.CategoryVo;
import com.tsy.blog.web.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long id) {
        final Category category = categoryMapper.selectById(id);
        return copy(category);
    }

    @Override
    public Result findAllCategories() {
        final List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categories));
    }

    private CategoryVo copy(Category category) {
        final CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> res = new ArrayList<>();
        categories.forEach(category -> res.add(copy(category)));
        return res;
    }
    
}
