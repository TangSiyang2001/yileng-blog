package com.tsy.blog.service;

import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.CommentParam;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
public interface CommentService {

    /**
     * 根据文章id获取对应评论
     * @param articleId 文章id
     * @return 消息模型
     */
    Result listComments(Long articleId);

    /**
     *
     * @param comment
     * @return
     */
    Result publishComment(CommentParam comment);
}
