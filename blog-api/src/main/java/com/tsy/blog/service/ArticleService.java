package com.tsy.blog.service;

import com.tsy.blog.web.vo.Result;
import com.tsy.blog.web.vo.params.ArticleParam;
import com.tsy.blog.web.vo.params.PageParam;

/**
 * @author Steven.T
 * @date 2021/10/8
 */
public interface ArticleService {
    /**
     * 文章列表分页服务
     * @param pageParam 分页参数
     * @return 消息模型
     */
    Result listArticles(PageParam pageParam);

    /**
     * 获取最热文章
     * @param limit 名额数
     * @return 消息模型
     */
    Result listPopularArticles(int limit);

    /**
     * 获取最新文章
     * @param limit 名额数
     * @return 消息模型
     */
    Result listLastArticles(int limit);

    /**
     * 文章归档
     * @return 消息模型
     */
    Result listArchives();

    /**
     * 查找文章
     * @param id id
     * @return 消息模型
     */
    Result findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam 文章参数
     * @return 消息模型
     */
    Result publishArticle(ArticleParam articleParam);
}
