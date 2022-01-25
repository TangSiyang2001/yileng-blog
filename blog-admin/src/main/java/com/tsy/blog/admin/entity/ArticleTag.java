package com.tsy.blog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ms_article_tag")
@ApiModel(value="ArticleTag对象", description="")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long articleId;

    private Long tagId;


}
