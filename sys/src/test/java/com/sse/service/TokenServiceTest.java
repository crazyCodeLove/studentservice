package com.sse.service;

import cn.hutool.core.lang.Assert;
import com.sse.model.user.User;
import com.sse.service.token.JWTTokenService;
import com.sse.service.token.TokenService;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

/**
 * author pczhao
 * date 2019-01-17 13:45
 */

public class TokenServiceTest {
    private TokenService tokenService = new JWTTokenService();

    @Test
    public void createTokenTest() {
        User srcUser = User.builder()
                .uid(new Random().nextLong())
                .birthday(new Date())
                .email("nice@163.com")
                .password("well")
                .telphone("1323543")
                .build();
        long expire = 3600000L;
        String token = tokenService.getToken(srcUser, expire);
        System.out.println("token:" + token);
        User desUser = tokenService.parseToken2User(token);
    }


}
