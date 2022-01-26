package com.tsy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.blog.dao.dto.Tag;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/9
 * 在下面的接口都在xml中有对应实现，未实现的简单操作由mybatisPlus提供
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 获取文章标签
     * @param articleId id
     * @return 结果
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 根据关联表中关联文章数前limit名取得标签的id列表
     * @param limit 名额数
     * @return 标签id列表
     */
    List<Long> findTagIdsByPopularity(int limit);

    /**
     * 根据id列表查询相应的标签列表
     * @param idList id列表
     * @return 标签列表
     */
    List<Tag> findTagsByIds(List<Long> idList);
}
