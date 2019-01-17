package com.sse.service.token;

import cn.hutool.crypto.SecureUtil;
import com.sse.model.User;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 9:26
 */

public interface TokenService {

    String KEY = "123456!@#$";
    String USER = "user";

    String getToken(User user, long expireMills);

    User parseToken2User(String token);

    default String getKey() {
        return SecureUtil.md5(KEY);
    }
}
