package com.tsy.blog.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steven.T
 * @date 2021/10/7
 */
@Configuration(proxyBeanMethods = false)
@MapperScan("com.tsy.blog.dao.mapper")
public class MybatisConfiguration {
    /**
     * 注册MybatisPlus分页插件
     * @return MybatisPlusInterceptor
     */
    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
