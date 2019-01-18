package com.sse.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pczhao
 * @email
 * @date 2018-12-10 16:13
 */

public class Demo1 {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
    }

    private static void fun10() {
        String u = "";
        User user = null;
        try {
            user = objectMapper.readValue(u, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }

    private static void fun9() throws IOException {
        User user = User.builder().uid("u1000").name("good").age(20).addr("sh").build();
        String key = "qwert123456";
        String token = createToken(user.getUid(), user, 3600000L, key);
        System.out.println("token:" + token);
        Claims claims = parseJWT(token, key);
        System.out.println(claims.getSubject());
        User uout = objectMapper.readValue((String) claims.get("user"), User.class);
        System.out.println(uout);
    }

    /**
     * @param subject
     * @param user
     * @param activeDuration 有效期，单位 ms
     * @param secretKey
     * @return
     */
    public static String createToken(String subject, User user, long activeDuration, String secretKey) throws JsonProcessingException {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        long ttlMillis = nowMillis + activeDuration;
        Date now = new Date(nowMillis);
        Date exp = new Date(ttlMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", objectMapper.writeValueAsString(user));
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims) // payload 自定义属性，如果有私有声明，一定要先设置这个自己创建的私有的声明
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now) // 签发时间
                .setExpiration(exp) // 过期时间
                .setSubject(subject) // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙// 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        return jwtBuilder.compact();
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User {
        private String uid;
        private String name;
        private String addr;
        private Integer age;
    }

    public static Claims parseJWT(String jwt, String key) {
        return Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)  //设置签名的秘钥
                .parseClaimsJws(jwt)  //设置需要解析的jwt
                .getBody();
    }

    private static String getDesEncryptHex(String content, String key) {
        DES des = SecureUtil.des(key.getBytes());
        return des.encryptHex(content);
    }

    public static void fun8() {
        String key = "niceKey@#";
        DES des = SecureUtil.des(key.getBytes());
        String content = "nice to meet you!";
        System.out.println("content:" + content);
        String encrypt = des.encryptHex(content);
        System.out.println("encrypt:" + encrypt);
        String decrypt = des.decryptStr(encrypt);
        System.out.println("decrypt:" + decrypt);
    }

    public static void fun7() {
        long n = 15;
        long mod = 7;
        System.out.println((mod & n));
        System.out.println(n % 8);
    }

    public static void fun1() {
        int num = 200;
        System.out.println(Integer.toHexString(num));
        System.out.println();

        Date now;
        StringBuilder sb = new StringBuilder(160000);
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (int i = 0; i < 10000; i++) {
            now = new Date();
            sb.append(sdf.format(now));
        }
        System.out.println("last time:" + (System.currentTimeMillis() - start));
//        System.out.println(sb.toString());

        sb.setLength(0);
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            now = new Date();
            sb.append(Long.toHexString(now.getTime()));
        }
        System.out.println("last time:" + (System.currentTimeMillis() - start));

    }

    public static void fun2() {
        ArrayList<String> names = new ArrayList<>();
        names.add("well");
        names.add("nice");
        names.add("haha");
        System.out.println(names.get(3));
    }

    public static String fun3() {
        System.out.println("this is fun3");
        try {
            System.out.println("in try block");
            return "done";
        } finally {
            System.out.println("in finally block");
        }
    }

    public static void fun4() {
        ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<>();
        dataMap.put("name", "well");
        dataMap.put("age", "34");
        System.out.println(dataMap.get("name"));
    }

    public static void fun5() {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nums.add(i);
        }
        System.out.println(nums.toString());
        Collections.shuffle(nums);
        System.out.println(nums.toString());
    }

    public static void fun6() {
        List<String> names = new ArrayList<>();
        names.add("well");
        System.out.println(names);
    }

}
