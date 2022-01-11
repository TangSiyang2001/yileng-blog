package com.tsy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsy.blog.dao.entity.Article;
import com.tsy.blog.dao.entity.ArticleBody;
import com.tsy.blog.dao.entity.ArticleTag;
import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.dao.mapper.ArticleBodyMapper;
import com.tsy.blog.dao.mapper.ArticleMapper;
import com.tsy.blog.dao.mapper.ArticleTagMapper;
import com.tsy.blog.service.*;
import com.tsy.blog.utils.UserThreadLocalUtils;
import com.tsy.blog.vo.*;
import com.tsy.blog.vo.params.ArticleParam;
import com.tsy.blog.vo.params.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/9
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ThreadService threadService;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Value("${redis.key-prefix.viewCount}")
    private String viewCountRedisPrefix;

    @Override
    public Result listArticles(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        final IPage<Article> articlePage = articleMapper.listArticles(page, pageParam.getCategoryId(), pageParam.getTagId(),
                pageParam.getYear(), pageParam.getMonth());
        //数据列表
        final List<Article> records = articlePage.getRecords();
        return Result.success(copyList(records, true, true));
    }

    @Override
    public Result listPopularArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result listLastArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreatedDate)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result listArchives() {
        return Result.success(articleMapper.listArchives());
    }

    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return Result.fail(Result.CodeMsg.DATA_NOT_EXISTS);
        }
        //在此处做浏览量的异步更新
        threadService.updateViewCount(article);
        final ArticleVo articleVo = copy(article, true, true, true, true);
        articleVo.setViewCounts(redisTemplate.opsForValue().get(viewCountRedisPrefix+id));
        return Result.success(articleVo);
    }


    private ArticleVo copy(Article article, boolean hasTag, boolean hasAuthor, boolean hasBody, boolean hasCategories) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreatedDate()).toString("yyyy-MM-dd HH:mm"));
        if (hasTag) {
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (hasAuthor) {
            //author在Article实体类中只有author的id，body的id，要从系统用户表中查询
            SysUser author = sysUserService.findUserById(article.getAuthorId());
            UserVo userVo=new UserVo();
            userVo.setId(author.getId());
            userVo.setAvatar(author.getAvatar());
            userVo.setNickname(author.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (hasBody) {
            ArticleBody articleBody = findBodyByArticleId(article.getId());
            articleVo.setBody(new ArticleBodyVo(articleBody.getContent()));
        }
        if (hasCategories) {
            final CategoryVo categoryVo = categoryService.findCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> articles, boolean hasTag, boolean hasAuthor) {
        List<ArticleVo> res = new ArrayList<>();
        for (Article article : articles) {
            res.add(copy(article, hasTag, hasAuthor, false, false));
        }
        return res;
    }

    private ArticleBody findBodyByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, articleId);
        return articleBodyMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result publishArticle(ArticleParam articleParam) {
        //文章合法性检验可增加拦截器
        Article article = new Article();
        final SysUser onlineUser = UserThreadLocalUtils.get();
        //just summary and title are copied
        BeanUtils.copyProperties(articleParam, article);
        article.setViewCounts(0);
        article.setCommentCounts(0);
        article.setAuthorId(onlineUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setWeight(Article.ARTICLE_COMMON);
        article.setCreatedDate(System.currentTimeMillis());
        articleMapper.insert(article);
        //bodyId先存入body表再取出
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(articleParam.getBody(), articleBody);
        //上面插入article后类中已经回写了id
        final Long articleId = article.getId();
        articleBody.setArticleId(articleId);
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        //关联表插入（insert是否要优化，如何批量插入？）
        for (TagVo tag : articleParam.getTags()) {
            articleTagMapper.insert(new ArticleTag(articleId, tag.getId()));
        }
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }
}
