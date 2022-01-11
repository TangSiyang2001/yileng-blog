package com.tsy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsy.blog.dao.mapper.ArticleMapper;
import com.tsy.blog.dao.mapper.CommentMapper;
import com.tsy.blog.dao.entity.Comment;
import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.service.CommentService;
import com.tsy.blog.service.SysUserService;
import com.tsy.blog.service.ThreadService;
import com.tsy.blog.utils.UserThreadLocalUtils;
import com.tsy.blog.vo.CommentVo;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.UserVo;
import com.tsy.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ThreadService threadService;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result listComments(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId)
                //要所有的一级评论，不然会出问题
                .eq(Comment::getLevel, 1);
        final List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result publishComment(CommentParam commentParam) {
        if (!StringUtils.hasLength(commentParam.getContent())) {
            return Result.fail(Result.CodeMsg.PARAMS_ERROR);
        }
        Comment comment = new Comment();
        final SysUser onlineUser = UserThreadLocalUtils.get();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(onlineUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        final Long parentId = commentParam.getParent();
        comment.setLevel(parentId == null || parentId == 0 ? 1 : 2);
        comment.setParentId(parentId == null || parentId == 0 ? 0L : parentId);
        final Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        threadService.updateCommentCount(articleMapper,comment.getArticleId());
        return Result.success(null);
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        final UserVo commentAuthor = sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(commentAuthor);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        commentVo.setChildren(findReplyByParentId(comment.getId()));
        if (comment.getLevel() > 1) {
            final Long toUid = comment.getToUid();
            final UserVo userVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVo);
        }
        return commentVo;
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> res = new ArrayList<>();
        for (Comment comment : comments) {
            res.add(copy(comment));
        }
        return res;
    }

    /**
     * 根据父评论id找到回复（目前仅支持两层评论，即评论、回复）
     *
     * @param parentId 父评论id
     * @return 回复
     */
    private List<CommentVo> findReplyByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId).eq(Comment::getLevel, 2);
        //不会有死循环递归，copyList对于空list会直接返回，不会再调用copy然后死循环
        return copyList(commentMapper.selectList(queryWrapper));
    }

}
