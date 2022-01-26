package com.tsy.blog.config;

import com.tsy.blog.web.handler.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/7
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    /**
     * 自定义跨域规则，允许来自8080端口的前端项目访问
     *
     * @param registry -
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //allowedOrigins传入变长参数，而不是链式法则
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8081",
                        "http://yileng.top",
                        "http://blog.yileng.top"
                );
    }

    /**
     * 注册拦截器
     *
     * @param registry -
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //评论需登录
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }
}
