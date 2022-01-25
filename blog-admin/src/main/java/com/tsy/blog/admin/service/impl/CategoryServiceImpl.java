package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.entity.Category;
import com.tsy.blog.admin.mapper.CategoryMapper;
import com.tsy.blog.admin.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
