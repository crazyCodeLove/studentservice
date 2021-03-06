package com.sse.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;

import java.nio.charset.StandardCharsets;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-04-19 13:56
 */

public class EncryptUtil {

    public static String desEncrypt(String content, String key) {
        Assert.isTrue(key != null && key.length() > 8, "key length should more than 8");
        DES des = SecureUtil.des(key.getBytes());
        return des.encryptHex(content, StandardCharsets.UTF_8.name());
    }

    public static String desDecrypt(String encryptContent, String key) {
        Assert.isTrue(key != null && key.length() > 8, "key length should more than 8");
        DES des = SecureUtil.des(key.getBytes());
        return des.decryptStr(encryptContent, StandardCharsets.UTF_8);
    }

    public static String aesEncrypt(String content, String key) {
        Assert.isTrue(key != null && key.length() % 16 == 0, "key length should be multiples of the number 16");
        AES aes = SecureUtil.aes(key.getBytes());
        return aes.encryptHex(content, StandardCharsets.UTF_8.name());
    }

    public static String aesDecrypt(String encryptContent, String key) {
        Assert.isTrue(key != null && key.length() % 16 == 0, "key length should be multiples of the number 16");
        AES aes = SecureUtil.aes(key.getBytes());
        return aes.decryptStr(encryptContent, StandardCharsets.UTF_8);
    }
}
