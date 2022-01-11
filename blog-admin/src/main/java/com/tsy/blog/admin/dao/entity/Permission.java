package com.tsy.blog.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限路径
     */
    private String path;

}
