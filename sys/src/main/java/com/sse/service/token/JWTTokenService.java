package com.sse.service.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.exception.token.JWTParseException;
import com.sse.exception.token.TokenExpireException;
import com.sse.exception.token.UserParseException;
import com.sse.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * author pczhao
 * date 2019-01-17 9:52
 */

@Service
@Slf4j
public class JWTTokenService implements TokenService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getToken(User user, long expireMills) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        long ttlMillis = nowMillis + expireMills;
        Date now = new Date(nowMillis);
        Date exp = new Date(ttlMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        String uStr = "";
        try {
            uStr = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error("转换 user 为 string 异常", e);
        }
        claims.put(USER, uStr);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims) // payload 自定义属性，如果有私有声明，一定要先设置这个自己创建的私有的声明
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now) // 签发时间
                .setExpiration(exp) // 过期时间
                .setSubject(user.getUid()) // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, getKey()); // 签名算法以及密匙// 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        return jwtBuilder.compact();
    }

    @Override
    public User parseToken2User(String token) {
        User user = null;
        try {
            Claims body = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(getKey())  //设置签名的秘钥
                    .parseClaimsJws(token)  //设置需要解析的jwt
                    .getBody();
            user = objectMapper.readValue(body.get(USER, String.class), User.class);
        } catch (ExpiredJwtException e) {
            throw new TokenExpireException("token 过期", e);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JWTParseException("token 解析异常", e);
        } catch (IOException e) {
            throw new UserParseException("token 中用户信息解析异常", e);
        }
        return user;
    }
}
