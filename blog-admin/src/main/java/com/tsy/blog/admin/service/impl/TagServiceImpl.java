package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.entity.Tag;
import com.tsy.blog.admin.mapper.TagMapper;
import com.tsy.blog.admin.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
