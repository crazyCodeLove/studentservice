package com.sse.util;

import org.junit.Test;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-04-30 13:31
 */

public class EncryptTest {

    @Test
    public void desTest() {
        String content = "nice to meet you";
        String key  = "12345678";
        String encrypt = EncryptUtil.desEncrypt(content, key);
        System.out.println(encrypt);
        String result = EncryptUtil.desDecrypt(encrypt, key);
        System.out.println(result);
    }

    @Test
    public void aesTest() {
        String content = "nice to meet you";
        String key  = "12345678qwertyui";
        String encrypt = EncryptUtil.aesEncrypt(content, key);
        System.out.println(encrypt);
        String result = EncryptUtil.aesDecrypt(encrypt, key);
        System.out.println(result);
    }
}
