package com.sse.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.sse.model.user.User;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 * author pczhao<br/>
 * date  2019-08-28 14:14
 */

public class JsonObjectUtilTest {

    @Test
    public void convertToJsonTest() {
        User user = null;
        String result = JsonObjectUtil.convertObjectToJson(user);
        System.out.println(result);
    }

    @Test
    public void convertToTypeTest() {
        User user = null;
        String json = JsonObjectUtil.convertObjectToJson(user);
        System.out.println("json:" + json);
        User result = JsonObjectUtil.convertJsonToType(json, User.class);
        System.out.println("result:" + result);
        System.out.println(DigestUtil.md5Hex("nice"));
        System.out.println(DigestUtil.md5Hex("nice "));
        System.out.println(DigestUtil.md5Hex("nice1"));

    }
}
