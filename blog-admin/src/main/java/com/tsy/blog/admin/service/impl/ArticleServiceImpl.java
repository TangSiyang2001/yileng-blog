package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.entity.Article;
import com.tsy.blog.admin.mapper.ArticleMapper;
import com.tsy.blog.admin.service.IArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
