package com.tsy.blog.service.impl;

import com.tsy.blog.dao.mapper.TagMapper;
import com.tsy.blog.dao.dto.Tag;
import com.tsy.blog.service.TagService;
import com.tsy.blog.web.vo.Result;
import com.tsy.blog.web.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/9
 */

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        return copyList(tagMapper.findTagsByArticleId(articleId));
    }

    @Override
    public Result findPopularTags(int limit) {
        final List<Long> tagIds = tagMapper.findTagIdsByPopularity(limit);
        return Result.success(CollectionUtils.isEmpty(tagIds) ? Collections.emptyList() :
                copyList(tagMapper.findTagsByIds(tagIds)));
    }

    @Override
    public Result findAllTags() {
        final List<Tag> tags = tagMapper.selectList(null);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findTagById(Long id) {
        final Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

}
