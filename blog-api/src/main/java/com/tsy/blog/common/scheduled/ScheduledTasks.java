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

    /**
     * 定时任务，定时将redis中浏览量数据同步到mysql中
     * redisTemplate.keys失效bug已解决，问题参考:https://blog.csdn.net/bingguang1993/article/details/87889788
     *
     * 问题根源：在redis-cli中发现redisKey变成诸如“\xac\xed\x00\x05t\x00\x1eVIEW_COUNT_1453321189844848642”这样
     * 多了前缀的，故redisTemplate.keys(viewCountRedisPrefix + "*")无法匹配到，key不是我们预期的 key.getBytes(),
     * 而是调用了this.keySerializer().serialize(key)，而debug的结果，默认Serializer是JdkSerializationRedisSerializer
     * 具体的实现很清晰了，就是 ObjectOutputStream,这个东西就是Java中最原始的序列化反序列流工具，会包含类型信息，所以会带上那串前缀了
     */
    @Scheduled(fixedRate = 10 * 1000)
    public void storeViewCount(){
        final Set<String> keys = redisTemplate.keys(viewCountRedisPrefix + "*");
        log.error("开始");
        if (!CollectionUtils.isEmpty(keys)) {
            log.error("记录");
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
