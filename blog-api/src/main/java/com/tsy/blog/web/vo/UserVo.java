package com.tsy.blog.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@Data
public class UserVo {

    /**
     * 分布式id较长，将id转为string，防止前端精度损失
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String avatar;

}
