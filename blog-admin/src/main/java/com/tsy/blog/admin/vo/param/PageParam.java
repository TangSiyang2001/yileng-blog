package com.tsy.blog.admin.vo.param;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Data
public class PageParam {

    private int page=1;

    private int limit =10;

    private String username;

    private Integer roleId;

    private String phoneNum;

    private String email;

}
