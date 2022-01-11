package com.tsy.blog.dao.entity;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/8
 */

@Data
public class Article {
    public static final Integer ARTICLE_TOP = 1;

    public static final Integer ARTICLE_COMMON = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createdDate;
}
