package com.tsy.blog.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steven.T
 * @date 2022/1/25
 */
@Configuration
public class JacksonConfiguration {

    /**
     * 全局处理Long型id失真
     * @return ObjectMapper
     */
    @Bean
    ObjectMapper objectMapper(){
        final ObjectMapper objectMapper = new ObjectMapper();
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
