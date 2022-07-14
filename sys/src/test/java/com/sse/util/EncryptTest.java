package com.sse.util;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-04-30 13:31
 */

@Slf4j
public class EncryptTest {

    @Test
    public void desTest() {
        String content = "nice to meet you";
        String key = "12345678";
        String encrypt = EncryptUtil.desEncrypt(content, key);
        System.out.println(encrypt);
        String result = EncryptUtil.desDecrypt(encrypt, key);
        System.out.println(result);
    }

    @Test
    public void aesTest() {
        String content = "nice to meet you";
        String key = "12345678qwertyui";
        String encrypt = EncryptUtil.aesEncrypt(content, key);
        System.out.println(encrypt);
        String result = EncryptUtil.aesDecrypt(encrypt, key);
        System.out.println(result);
    }

    @Test
    public void md5FileTest() {
        String filename = "D:\\logs\\temp\\test1.txt";
        String md5Hex = DigestUtil.md5Hex(new File(filename));
        log.info("filename:[{}] md5:[{}]", filename, md5Hex);

        filename = "D:\\logs\\temp\\test2.txt";
        md5Hex = DigestUtil.md5Hex(new File(filename));
        log.info("filename:[{}] md5:[{}]", filename, md5Hex);

        filename = "D:\\logs\\temp\\test3.txt";
        md5Hex = DigestUtil.md5Hex(new File(filename));
        log.info("filename:[{}] md5:[{}]", filename, md5Hex);
    }
}
