package com.tsy.blog.dao.dto;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/8
 */
@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;

}