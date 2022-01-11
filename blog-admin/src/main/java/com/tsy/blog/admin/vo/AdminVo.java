package com.tsy.blog.admin.vo;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/11/10
 */
@Data
public class AdminVo {
    private Long id;

    private String username;

    /**
     * 记得要查询具体字段
     */
    private String role;

    private String createdDate;

    private String email;

    private String phoneNum;
}
