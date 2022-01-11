package com.tsy.blog.service;

import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.TagVo;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/9
 */
public interface TagService {
    /**
     * 根据文章id查询标签
     * @param articleId 文章标签
     * @return 标签列表
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 按热度搜索标签
     * @param limit 名额
     * @return 消息模型
     */
    Result findPopularTags(int limit);

    /**
     * 查询所有标签
     * @return 消息模型
     */
    Result findAllTags();

    /**
     * 根据id获取标签
     * @param id 标签id
     * @return 消息模型
     */
    Result findTagById(Long id);
}
