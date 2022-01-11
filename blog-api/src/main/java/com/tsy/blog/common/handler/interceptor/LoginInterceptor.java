package com.tsy.blog.common.handler.interceptor;

import com.alibaba.fastjson.JSON;
import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.service.SsoService;
import com.tsy.blog.utils.UserThreadLocalUtils;
import com.tsy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截需登录访问的资源
 * @author Steven.T
 * @date 2021/10/14
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private SsoService ssoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token=request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        SysUser sysUser;
        if(!StringUtils.hasLength(token) || (sysUser=ssoService.getUserInfoInCache(token))==null){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(JSON.toJSONString(Result.fail(Result.CodeMsg.NOT_LOGIN)));
            return  false;
        }
        //设置线程变量存用户，避免再从redis获取消耗网络资源
        UserThreadLocalUtils.set(sysUser);
        return true;
    }

    /**
     * 请求结束后及时释放线程变量，避免内存泄漏
     * @param request -
     * @param response -
     * @param handler -
     * @param ex -
     * @throws Exception -
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocalUtils.remove();
    }
}
