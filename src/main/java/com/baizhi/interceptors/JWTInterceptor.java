package com.baizhi.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baizhi.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, Object> map = new HashMap<>();
        String  token = request.getHeader("token");
        try{
            DecodedJWT verify = JWTUtils.verify(token);//验证令牌
            return true;
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            map.put("mag", "无效签名");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("mag", "token过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("mag", "token算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        map.put("state", false);
        //将map 转为json jackon
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
