package com.tsy.blog.web.vo.params;

import com.tsy.blog.web.vo.CategoryVo;
import com.tsy.blog.web.vo.TagVo;
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
