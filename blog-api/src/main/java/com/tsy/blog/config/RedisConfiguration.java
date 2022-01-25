package com.tsy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 问题参考：https://blog.csdn.net/bingguang1993/article/details/87889788
 *
 * @author Steven.T
 * @date 2022/1/21
 */
@Configuration
public class RedisConfiguration {

    /**
     * 提供客制化序列化器，避免原生序列化器造成的困扰
     * @return -
     */
    @Bean
    public RedisSerializer<Object> customizedRedisSerializer(){
        return new CustomizedSerializer();
    }

    /**
     * 也是必须的，专门提供给<String, Integer>类型（该类型用于定时任务更新浏览量）
     * @param redisConnectionFactory -
     * @return -
     */
    @Bean
    public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        // key的序列化采用自定义的CustomizedSerializer
        //可参考StringRedisTemplate的写法(选中RedisTemplate按ctrl+H可查看)
        //可尝试使用RedisSerializer接口中的方法
        template.setKeySerializer(new CustomizedSerializer());
        template.setHashKeySerializer(new CustomizedSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    private static class CustomizedSerializer implements RedisSerializer<Object> {
        private final Charset charset;

        CustomizedSerializer() {
            this(StandardCharsets.UTF_8);
        }

        public CustomizedSerializer(Charset charset) {
            Assert.notNull(charset, "Charset must not be null!");
            this.charset = charset;
        }


        @Override
        public byte[] serialize(Object o) throws SerializationException {
            return o == null ? null : String.valueOf(o).getBytes(charset);
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            return bytes == null ? null : new String(bytes, charset);

        }
    }

}
