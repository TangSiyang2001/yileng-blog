package com.tsy.blog.admin.dao.entity;

import lombok.Data;

/**
 * 关联表，由角色找权限
 * @author Steven.T
 * @date 2021/10/29
 */
@Data
public class RolePermission {

    private Long id;

    private Long roleId;

    private Long permissionId;
}
