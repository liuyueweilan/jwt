package com.baizhi.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String,Object> login(User user){

        log.info("用户名：[{}]",user.getUsername());
        log.info("密码：[{}]",user.getPassword());
        HashMap<String,Object> map = new HashMap<>();
        try{
            User login = userService.login(user);//不用cookies存储，使用jwt
            Map<String, String> payload = new HashMap<>();
            payload.put("username", login.getUsername());
            payload.put("password", login.getPassword());
            //生成JWT令牌
            String token = JWTUtils.getToken(payload);
            map.put("state", true);
            map.put("msg", "认证成功");
            map.put("token", token);//将token存储到客户端
        }catch (Exception e){
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String,Object> test(){
        HashMap<String,Object> map = new HashMap<>();

        //业务逻辑
        map.put("state", true);
        map.put("msg", "请求成功");

        return map;
    }
}
