package com.tsy.blog.dao.dto;

import lombok.Data;

/**
 * 文章、标签关联表对象
 *
 * @author Steven.T
 * @date 2021/10/20
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;

    public ArticleTag(Long articleId,Long tagId){
        this.articleId=articleId;
        this.tagId=tagId;
    }
}
