package com.tsy.blog.dao.entity;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/8
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}