package com.tsy.blog.common.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Steven.T
 * @date 2021/10/13
 */
public class JwtUtils {

    private static final String JWT_TOKEN = "StevenTSY!@#$$";

    private static final String UNDEFINED="undefined";

    private JwtUtils(){
        throw new IllegalStateException("This is a util class...");
    }

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>(1);
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 签发算法，秘钥为jwtToken
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN)
                // body数据，要唯一，自行设置
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 一天的有效时间
                .setExpiration(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 60 * 1000));
        return jwtBuilder.compact();
    }

    public static Map<String, Object> checkToken(String token){
        try {
            if(!StringUtils.hasLength(token) || Objects.equals(UNDEFINED,token)){
                return null;
            }
            return Jwts.parser()
                    .setSigningKey(JWT_TOKEN)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
