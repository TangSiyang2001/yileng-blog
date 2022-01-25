package com.tsy.blog.admin.service;

import com.tsy.blog.admin.common.response.ResponseResult;
import com.tsy.blog.admin.entity.ArticleBody;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
public interface IArticleBodyService extends IService<ArticleBody> {

    /**
     * 根据articleId查找body
     * @param articleId 文章id
     * @return 结果
     */
    ResponseResult getBodyByArticleId(Integer articleId);
}
