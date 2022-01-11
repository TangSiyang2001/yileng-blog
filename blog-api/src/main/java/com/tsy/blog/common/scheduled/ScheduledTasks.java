package com.tsy.blog.common.scheduled;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.blog.dao.entity.Article;
import com.tsy.blog.dao.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Steven.T
 * @date 2021/10/31
 */
@Slf4j
@Component
public class ScheduledTasks {

    @Resource
    private RedisTemplate<String,Integer> redisTemplate;

    @Resource
    private ArticleMapper articleMapper;

    @Value("${redis.key-prefix.viewCount}")
    private String viewCountRedisPrefix;

    @Scheduled(fixedRate = 3000 * 60)
    public void storeViewCount(){
        final Set<String> keys = redisTemplate.keys(viewCountRedisPrefix + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            for (String key : keys) {
                //根据下划线拆解原key获取articleId
                final String[] parts = key.split("_");
                Long articleId= Long.valueOf(parts[parts.length-1]);
                final Integer viewCount = redisTemplate.opsForValue().get(key);
                Article article=new Article();
                article.setId(articleId);
                article.setViewCounts(viewCount);
                LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
                updateWrapper.eq(Article::getId,articleId);
                //这里的update除了浏览量其他都为null，更新会更新非null属性，所以实体类中不能采用基本类型，即默认值为0，会影响更新结果
                articleMapper.update(article,updateWrapper);
                redisTemplate.delete(key);
            }
        }
    }

}
