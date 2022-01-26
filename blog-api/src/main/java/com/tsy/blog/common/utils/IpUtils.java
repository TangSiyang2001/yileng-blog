package com.tsy.blog.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Steven.T
 * @date 2021/10/20
 */
public class IpUtils {
    private IpUtils(){
        throw new IllegalStateException("This is a util class...");
    }

    public static String getIpAdder(HttpServletRequest request) {
        if(request==null){
            return "";
        }

        final String unknown = "unknown";
        String separator = ",";
        final int maxLength = 15;
        String ip = request.getHeader("x-forwarded-for");

        if (!StringUtils.hasLength(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //使用代理则获取第一个ip
        if (StringUtils.hasLength(ip) && ip.length() > maxLength) {
            int idx = ip.indexOf(separator);
            if (idx > 0) {
                ip = ip.substring(0, idx);
            }
        }

        return ip;

    }
}
