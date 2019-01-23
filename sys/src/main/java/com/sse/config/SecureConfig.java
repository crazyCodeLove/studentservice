package com.sse.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 14:08
 */

@Configuration
public class SecureConfig {
    private static String securKey;

    private static AES aes;

    public static String getSecurKey() {
        return securKey;
    }

    @Value("secure.key:1234qwer!@#$")
    public void setSecurKey(String securKey) {
        SecureConfig.securKey = securKey;
    }

    @PostConstruct
    private void init() {
        aes = SecureUtil.aes(securKey.getBytes());
    }

    public static String aesEncrypt(String content) {
        if (content == null) {
            return "";
        }
        return new String(aes.encrypt(content));
    }

    public static byte[] aesEncrypt(byte[] content) {
        if (content == null) {
            return new byte[0];
        }
        return aes.encrypt(content);
    }

    public static String aesDecrypt(String content) {
        if (content == null) {
            return "";
        }
        return new String(aes.decrypt(content));
    }

    public static byte[] aesDecrypt(byte[] content) {
        if (content == null) {
            return new byte[0];
        }
        return aes.decrypt(content);
    }

}
