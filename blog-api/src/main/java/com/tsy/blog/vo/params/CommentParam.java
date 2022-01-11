package com.tsy.blog.vo.params;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/19
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
