package com.sse.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.domain.EurekaApplication;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date 2018-11-06 16:11
 */

public class HttpClientUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json;charset=utf-8");

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 获取服务的所有地址（注册在 eureka server 上的服务）
     *
     * @param eurekaIp    ip
     * @param eurekaPort  port
     * @param servicename name
     * @return 服务列表
     */
    public static List<String> getAllServiceAddr(String eurekaIp, String eurekaPort, String servicename) {
        List<String> result = new ArrayList<>();
        String url = "http://" + eurekaIp + ":" + eurekaPort + "/eureka/apps/" + servicename;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("Content-Type", "application/json;charset:utf-8")
                .addHeader("Accept", "application/xml")
                .get()
                .build();//创建Request 对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseContent = response.body().string();
                Matcher matcher = Pattern.compile("<homePageUrl>(.+?)</homePageUrl>").matcher(responseContent);
                while (matcher.find()) {
                    String homepage = matcher.group(1).trim();
                    result.add(homepage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param defaultZone 形式类似于：http://127.0.0.1:8010/eureka/
     * @param servicename
     * @return
     */
    public static List<String> getAllServiceAddr(String defaultZone, String servicename) throws IOException {
        List<String> result = new ArrayList<>();
        String url = defaultZone + "apps/" + servicename;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("Content-Type", "application/json;charset:utf-8")
                .addHeader("Accept", "application/json")
                .get()
                .build();//创建Request 对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String content = response.body().string();
                EurekaApplication application = mapper.readValue(content, EurekaApplication.class);
                for (EurekaApplication.Application.Instance instance : application.getApplication().getInstance()) {
                    if ("UP".equals(instance.getStatus().trim().toUpperCase())) {
                        /** 添加所有启动着的服务的首地址 */
                        result.add(instance.getHomePageUrl());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public static <T> T postForEntity(String url, Object param, Class<T> responseType) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, mapper.writeValueAsString(param));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json;charset:utf-8")
                .addHeader("Accept", "application/json")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        T result = null;
        if (response.isSuccessful()) {
            result = mapper.readValue(response.body().toString(), responseType);
        }
        return result;
    }

    /**
     * 随机获取一个微服务的地址
     *
     * @param eurekaIp
     * @param eurekaPort
     * @param servicename
     * @return
     */
    public static String getOneServiceAddr(String eurekaIp, String eurekaPort, String servicename) {
        List<String> result = new ArrayList<>();
        String url = "http://" + eurekaIp + ":" + eurekaPort + "/eureka/apps/" + servicename;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/xml")
                .get()
                .build();//创建Request 对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseContent = response.body().string();
                Matcher matcher = Pattern.compile("<homePageUrl>(.+?)</homePageUrl>").matcher(responseContent);
                while (matcher.find()) {
                    String homepage = matcher.group(1).trim();
                    result.add(homepage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.isEmpty()) {
            return "";
        } else {
            Collections.shuffle(result);
            return result.get(0);
        }
    }

    public static void fun1() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8010/eureka/apps/SERVICE-PROVIDER")//请求接口。如果需要传参拼接到接口后面。
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/xml")
                .get()
                .build();//创建Request 对象

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                System.out.println(result);
                Matcher matcher = Pattern.compile("<homePageUrl>(.+?)</homePageUrl>").matcher(result);
                while (matcher.find()) {
                    String homepage = matcher.group(1).trim();
                    System.out.println(homepage);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getMethod1() {
        List<String> allServiceAddr = getAllServiceAddr("127.0.0.1", "8010", "SERVICE-PROVIDER");
        for (String url : allServiceAddr) {
            System.out.println(url);
        }
    }

    public static void getMethod2() throws IOException {
        List<String> allServiceAddr = getAllServiceAddr("http://127.0.0.1:8010/eureka/", "SERVICE-PROVIDER");
        for (String url : allServiceAddr) {
            System.out.println(url);
        }
    }

    public static void main(String[] args) {
        try {
            getMethod2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
