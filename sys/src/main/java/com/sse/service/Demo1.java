package com.sse.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.sse.util.ValidateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author pczhao <br/>
 * date 2018-12-10 16:13
 */

public class Demo1 {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

    }

    public static void determinePdfFile() {
        String filename1 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\慈溪市国有资产投资公司债券2018年报.pdf";
        String filename2 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\testpassword.pdf";
        String filename3 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\000208.pdf";
        String filename4 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\testonlyread.pdf";
        String filename5 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\000208.pdf";
        String filename6 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\001659p14.pdf";
        String filename7 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\069020.pdf";
        String filename8 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\573636.pdf";
        String filename9 = "D:\\chengxu\\java\\idea\\mvn\\studentservice\\sys\\src\\main\\resources\\pdffile\\maindoc.pdf";

        readPdfFile(filename1);
        readPdfFile(filename2);
        readPdfFile(filename3);
        readPdfFile(filename4);
        readPdfFile(filename5);
        readPdfFile(filename6);
        readPdfFile(filename7);
        readPdfFile(filename8);
        readPdfFile(filename9);
    }

    private static void readPdfFile(String filename) {
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(new File(filename));
            System.out.println("pdf open success. " + filename);
        } catch (IOException e) {
            System.out.println("pdf open failed. " + filename);
            e.printStackTrace();
        } finally {
            if (pdDocument != null) {
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fun12() {
        Set<Integer> set1 = Sets.newHashSet(12, 45, 78, 1, 2, 4, 5, 7, 9, 8, 6, 3);
        Set<Integer> set2 = Sets.newHashSet(12, 45, 78, 32, 65, 98);
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
    }

    private static void fun11() {
        Object users = new ArrayList<User>();
        ((ArrayList<User>) users).add(User.builder().name("nice").age(12).uid("1").build());
        ((ArrayList<User>) users).add(User.builder().name("well").age(12).uid("2").build());
        ((ArrayList<User>) users).add(User.builder().name("bad").age(12).build());

        ValidateUtil.validate(users);
        System.out.println("users");
        int i = 1;
        for (Object u : (Collection) users) {
            ValidateUtil.validate(u);
            System.out.println(i++);
        }
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
        User uout = objectMapper.readValue((String) claims.get("com/sse/model/user"), User.class);
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
        claims.put("com/sse/model/user", objectMapper.writeValueAsString(user));
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
        @NotNull(message = "uid 不能为空")
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
        int n = 23;

        long size = ObjectSizeCalculator.getObjectSize(n);
        System.out.println(size);
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


}
