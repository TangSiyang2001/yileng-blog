package com.tsy.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/30
 */
@Data
@AllArgsConstructor
public class PageVo<T> {

    private int count;

    private List<T> data;
}
