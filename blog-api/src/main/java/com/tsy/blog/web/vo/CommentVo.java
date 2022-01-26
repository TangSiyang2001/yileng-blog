package com.tsy.blog.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@Data
public class CommentVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo author;

    private String content;

    /**
     * 原来多了s改成正确
     */
    private List<CommentVo> children;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
