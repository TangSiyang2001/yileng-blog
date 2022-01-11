package com.tsy.blog.admin.vo.param;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Data
public class AdminParam {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNum;

    private Long roleId;
}
