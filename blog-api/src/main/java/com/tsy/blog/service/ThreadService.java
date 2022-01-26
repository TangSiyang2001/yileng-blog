package com.tsy.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.blog.dao.dto.Article;
import com.tsy.blog.dao.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@Component
public class ThreadService {

    @Value("${redis.key-prefix.viewCount}")
    private String viewCountRedisPrefix;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 该方法被调用后自动被抛入线程池中执行
     * @param article       文章对象
     */
    @Async("taskExecutor")
    public synchronized void updateViewCount(Article article) {
        final Long articleId = article.getId();
        final String redisKey = viewCountRedisPrefix + articleId;
        final Integer viewCount;
        if ((viewCount = redisTemplate.opsForValue().get(redisKey)) != null) {
            redisTemplate.opsForValue().set(redisKey, viewCount + 1);
        } else {
            redisTemplate.opsForValue().set(redisKey, article.getViewCounts() + 1);
        }
    }

    /**
     * 评论并发量不那么高，用乐观锁操作即可
     *
     * @param articleMapper 文章数据操作接口
     * @param articleId     文章对象id
     */
    @Async("taskExecutor")
    public void updateCommentCount(ArticleMapper articleMapper, Long articleId) {
        Article update = new Article();
        final Article article = articleMapper.selectById(articleId);
        update.setId(articleId);
        update.setCommentCounts(article.getCommentCounts() + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, articleId)
                //乐观锁操作，判断数据库中记录是否被其他线程改变，若被其他线程改变，则不再增加
                .eq(Article::getCommentCounts, article.getCommentCounts());
        articleMapper.update(update, updateWrapper);
    }
}
