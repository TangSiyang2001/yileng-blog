package com.tsy.blog.vo.params;

import com.tsy.blog.vo.CategoryVo;
import com.tsy.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/19
 */
@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
