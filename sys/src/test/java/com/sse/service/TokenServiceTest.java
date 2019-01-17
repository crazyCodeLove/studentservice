package com.sse.service;

import com.sse.model.User;
import com.sse.service.token.JWTTokenService;
import com.sse.service.token.TokenService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author pczhao
 * @email
 * @date 2019-01-17 13:45
 */

public class TokenServiceTest {
    private TokenService tokenService = new JWTTokenService();

    @Test
    public void createTokenTest() {
        User srcUser = User.builder()
                .uid(UUID.randomUUID().toString())
                .birthday(new Date())
                .email("nice@163.com")
                .password("well")
                .telphone("1323543")
                .build();
        long expire = 3600000L;
        String token = tokenService.getToken(srcUser, expire);
        System.out.println("token:" + token);
        User desUser = tokenService.parseToken2User(token);
        userEquals(srcUser, desUser);
    }

    private void userEquals(User user1, User user2) {
        Assert.assertEquals(user1.getUid(), user2.getUid());
        Assert.assertEquals(user1.getEmail(), user2.getEmail());
        Assert.assertEquals(user1.getPassword(), user2.getPassword());
        Assert.assertEquals(user1.getTelphone(), user2.getTelphone());
        Assert.assertEquals(user1.getUsername(), user2.getUsername());
        Assert.assertEquals(user1.getBirthday().getTime(), user2.getBirthday().getTime());
    }


}
