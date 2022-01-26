package com.tsy.blog.common.utils;

import com.tsy.blog.dao.dto.SysUser;

/**
 * @author Steven.T
 * @date 2021/10/15
 */
public class UserThreadLocalUtils {

     private static final ThreadLocal<SysUser> LOCAL=new ThreadLocal<>();

     private UserThreadLocalUtils(){
          throw new IllegalStateException("This is a util class...");
     }

     public static void set(SysUser user){
          LOCAL.set(user);
     }

     public static SysUser get(){
          return LOCAL.get();
     }

     public static void remove(){
          LOCAL.remove();
     }
}
