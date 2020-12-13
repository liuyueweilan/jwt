import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
class JwtApplicationTests {

    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,60);
        String token = JWT.create()
                .withHeader(map)//header
                .withClaim("userId", 21)
                .withClaim("username", "xiaochen")//payload
                .withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256("^$#@%&(7"));//签名

        System.out.println(token);
    }


    @Test
    public void test(){

        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("^$#@%&(7")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDc4MjQ2MTIsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoieGlhb2NoZW4ifQ.e0p3HvAQUoLfMgYltKjDM_n8gnP1NsHfPz7rci5e0nc");
        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("username").asString());
    }

}
