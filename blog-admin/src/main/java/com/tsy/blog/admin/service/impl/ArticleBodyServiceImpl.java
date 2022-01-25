package com.tsy.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsy.blog.admin.common.response.ResponseResult;
import com.tsy.blog.admin.entity.ArticleBody;
import com.tsy.blog.admin.mapper.ArticleBodyMapper;
import com.tsy.blog.admin.service.IArticleBodyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements IArticleBodyService {

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ResponseResult getBodyByArticleId(Integer articleId) {
        final ArticleBody articleBody = articleBodyMapper.selectOne(
                new LambdaQueryWrapper<ArticleBody>()
                        .eq(ArticleBody::getArticleId, articleId)
        );
        return ResponseResult.success(articleBody);
    }
}
