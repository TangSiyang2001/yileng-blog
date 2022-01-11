package com.tsy.blog.dao.entity;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/8
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
