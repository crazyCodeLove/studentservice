package com.sse.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static okhttp3.Request.Builder;

/**
 * author pczhao
 * email pczhao@sse.com.cn
 * date  2019-04-26 13:50
 */

@Slf4j
public class HttpUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T getOfType(String url, Map<String, String> headers, Map<String, String> params, Class<T> clazz) {
        T result = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = buildRequestBuilder(url, headers, params).build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && clazz != null) {
                String body = response.body().string();
                log.info("url: {}, response body: {}", url, body);
                result = mapper.readValue(body, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    private static Builder buildRequestBuilder(String url, Map<String, String> headers, Map<String, String> params) {
        String requestUrl = buildUrl(url, params);
        Builder rb = new Request.Builder().url(requestUrl);
        rb = buildHeaderJsonContent(rb);
        return buildHeader(rb, headers);
    }

    private static String buildUrl(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        return sb.toString();
    }

    private static Builder buildHeaderJsonContent(Builder requestBuilder) {
        Map<String, String> jsonHeaders = new HashMap<>();
        jsonHeaders.put("Content-Type", "application/json");
        jsonHeaders.put("Accept", "application/json");
        return buildHeader(requestBuilder, jsonHeaders);
    }

    private static Builder buildHeader(Builder requestBuilder, Map<String, String> headers) {
        if (requestBuilder != null && headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return requestBuilder;
    }
}
