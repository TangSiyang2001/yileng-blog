package com.tsy.blog.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Data
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private Integer roleId;

    private Long createdDate;

    private Integer status;

    private String email;

    private String phoneNum;

    public static final class Status{
        public static final Integer NORMAL=0;
        public static final Integer DELETED=1;
    }
}
