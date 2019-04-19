package com.sse.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-04-19 13:56
 */

public class EncryptUtil {

    public static String desEncrypt(String content, String key) {
        DES des = SecureUtil.des(key.getBytes());
        return des.encryptHex(content);
    }

    public static String desDecrypt(String encryptContent, String key) {
        DES des = SecureUtil.des(key.getBytes());
        return des.decryptStr(encryptContent);
    }
}
