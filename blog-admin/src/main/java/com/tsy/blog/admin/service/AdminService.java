package com.tsy.blog.admin.service;

import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.AdminParam;
import com.tsy.blog.admin.vo.param.PageParam;
import org.springframework.stereotype.Service;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Service
public interface AdminService {
    Result registerAdmin(AdminParam admin);

    Result deleteAdminById(Long id);

    Result updateAdminInfo(AdminParam adminParam);

    Result findAdminById(Long id);

    Result listAdmins(PageParam pageParam);
}
