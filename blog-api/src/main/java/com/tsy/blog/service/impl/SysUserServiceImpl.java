package com.tsy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsy.blog.dao.mapper.SysUserMapper;
import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.service.SsoService;
import com.tsy.blog.service.SysUserService;
import com.tsy.blog.vo.LoginUserVo;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/10
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SsoService ssoService;
    @Override
    public SysUser findUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            user = new SysUser();
            user.setNickname("未知");
        }
        return user;
    }

    @Override
    public SysUser findUserByInfo(String account, String pwd) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, pwd)
                .select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname)
                .last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 1.检验token合法性
     * 2.在redis查找是否有相应信息
     *
     * @param token token参数
     * @return 消息模型
     */
    @Override
    public Result getUserInfoByToken(String token) {
        final SysUser onlineUser = ssoService.getUserInfoInCache(token);
        if(onlineUser==null){
            return Result.fail(Result.CodeMsg.INVALID_TOKEN);
        }
        final LoginUserVo onlineUserInfo =
                new LoginUserVo(onlineUser.getId(), onlineUser.getAccount(),
                        onlineUser.getNickname(), onlineUser.getAvatar());
        return Result.success(onlineUserInfo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account).last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser){
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        final SysUser user = findUserById(id);
        UserVo userVo =new UserVo();
        userVo.setId(id);
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }


}
