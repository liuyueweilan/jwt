package com.baizhi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static final String SIGN = "^$#@%&(7";

    //生成token  header.payload.sing
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);//默认7天

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(SIGN));//sign


//        String token = JWT.create()
//                .withClaim("userId", 21)//payload
//                .withClaim("userName", "xiaochen")
//                .withExpiresAt(instance.getTime())
//                .sign(Algorithm.HMAC256(SIGN));//签名

        return token;
    }

    //验证token

    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }


//    //获取token信息
//    public static DecodedJWT getTokenInfo(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
//        return verify;
//    }

}
