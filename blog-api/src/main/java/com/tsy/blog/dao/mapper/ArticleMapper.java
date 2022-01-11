package com.tsy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsy.blog.dao.entity.Archives;
import com.tsy.blog.dao.entity.Article;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/8
 * 在下面的接口都在xml中有对应实现，未实现的简单操作由mybatisPlus提供
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 实现归档查询
     *
     * @return 归档列表
     */
    List<Archives> listArchives();

    IPage<Article> listArticles(Page<Article> page,
                       Long categoryId,
                       Long tagId,
                       String year,
                       String month);
}
