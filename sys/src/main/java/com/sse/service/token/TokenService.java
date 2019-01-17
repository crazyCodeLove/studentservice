package com.sse.service.token;

import cn.hutool.crypto.SecureUtil;
import com.sse.model.User;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 9:26
 */

public interface TokenService {

    /** 用于签名算法，使用 md5 生成 key */
    String KEY = "123456!@#$";

    /** 放到 claims Map 中的用户信息 key */
    String USER = "user";

    String getToken(User user, long expireMills);

    User parseToken2User(String token);

    default String getKey() {
        return SecureUtil.md5(KEY);
    }
}
