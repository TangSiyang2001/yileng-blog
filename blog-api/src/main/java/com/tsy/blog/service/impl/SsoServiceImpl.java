package com.tsy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.service.SsoService;
import com.tsy.blog.service.SysUserService;
import com.tsy.blog.utils.JwtUtils;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.SsoParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven.T
 * @date 2021/10/13
 */
@Service
public class SsoServiceImpl implements SsoService {

    private static final String SALT = "StevenTSY!@#$$";

    @Value("${redis.key-prefix.token}")
    private String tokenRedisPrefix;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1.判断信息是否合法
     * 2.用用户名密码去查数据库中是否存在，不存在就登录失败
     * 3.若存在，生成token返回给前端
     * 4.在token中存redis，包括用户信息和过期时间
     * （登录时会先认证先判断token字符串是否合法，再看redis当中有没有有效token，相应功能位于SysUSerController中的getUserInfoByToken()中）
     *
     * @param ssoParam 登录信息
     * @return 消息模型
     */
    @Override
    public Result login(SsoParam ssoParam) {
        String account = ssoParam.getAccount();
        String password = DigestUtils.md5Hex(ssoParam.getPassword() + SALT);
        final SysUser user = sysUserService.findUserByInfo(account, password);
        if (user == null) {
            return Result.fail(Result.CodeMsg.ACCOUNT_PASSWORD_NOT_EXIST);
        }
        String token = JwtUtils.createToken(user.getId());
        redisTemplate.opsForValue().set(tokenRedisPrefix + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete(tokenRedisPrefix + token);
        return Result.success(null);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(SsoParam ssoParam) {
        final String account = ssoParam.getAccount();
        final String password = ssoParam.getPassword();
        final String nickname = ssoParam.getNickName();
        if (sysUserService.findUserByAccount(account) != null) {
            return Result.fail(Result.CodeMsg.EXISTED_ACCOUNT);
        }
        SysUser sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + SALT));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        //1 为true
        sysUser.setAdmin(1);
        // 0 为false
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);
        //save后会生成id吗？答：会的，mybatis插入完成后主键会自动回写 到实体类中去
        final String token = JwtUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set(tokenRedisPrefix + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser getUserInfoInCache(String token) {
        //检验token合法性
        if (JwtUtils.checkToken(token) == null) {
            return null;
        }
        //在redis查找是否有相应token
        final String jsonInfo = redisTemplate.opsForValue().get(tokenRedisPrefix + token);
        if (!StringUtils.hasLength(jsonInfo)) {
            return null;
        }
        return JSON.parseObject(jsonInfo, SysUser.class);
    }
}
