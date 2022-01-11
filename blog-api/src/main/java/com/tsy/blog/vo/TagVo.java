package com.tsy.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/9
 * 标签
 */
@Data
public class TagVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String avatar;

    private String tagName;

}