package com.sse.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static okhttp3.Request.Builder;

/**
 * author pczhao
 * date  2019-04-26 13:50
 */

@Slf4j
public class HttpUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int CONNECT_TIME_OUT_IN_SEC = 30;
    private static final int READ_TIME_OUT_IN_SEC = 60;
    private static final int WRITE_TIME_OUT_IN_SEC = 60;

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 请求失败会返回 null
     *
     * @param url     请求的 url
     * @param headers 请求头参数， key:value 对
     * @param params  请求参数， key:value 对
     * @param clazz   返回对象类型
     */
    public static <T> T getForType(String url, Map<String, String> headers, Map<String, String> params, Class<T> clazz) {
        OkHttpClient okHttpClient = buildHttpClient();
        Request request = buildGetRequestBuilder(url, headers, params).build();
        Call call = okHttpClient.newCall(request);
        return executeCall(call, clazz);
    }

    /**
     * 请求失败会返回 null
     *
     * @param url           请求的 url
     * @param headers       请求头参数， key:value 对
     * @param params        请求参数， key:value 对
     * @param typeReference 返回对象类型
     */
    public static <T> T getForType(String url, Map<String, String> headers, Map<String, String> params, TypeReference<T> typeReference) {
        OkHttpClient okHttpClient = buildHttpClient();
        Request request = buildGetRequestBuilder(url, headers, params).build();
        Call call = okHttpClient.newCall(request);
        return executeCall(call, typeReference);
    }

    /**
     * 请求失败会返回 null
     *
     * @param url     请求的 url
     * @param headers 请求头参数， key:value 对
     * @param param   请求体对象
     * @param clazz   返回对象类型
     */
    public static <T> T postForType(String url, Map<String, String> headers, Object param, Class<T> clazz) {
        OkHttpClient okHttpClient = buildHttpClient();
        Request request = buildPostRequestBuilder(url, headers, param).build();
        Call call = okHttpClient.newCall(request);
        return executeCall(call, clazz);
    }

    public static <T> T postForType(String url, Map<String, String> headers, Object param, TypeReference<T> typeReference) {
        OkHttpClient okHttpClient = buildHttpClient();
        Request request = buildPostRequestBuilder(url, headers, param).build();
        Call call = okHttpClient.newCall(request);
        return executeCall(call, typeReference);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T executeCall(Call call, Object type) {
        T result = null;
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && type != null) {
                String body = response.body().string();
                log.info("url: {}, response body: {}", call.request().url().toString(), body);
                if (type instanceof TypeReference) {
                    result = convertResult(body, (TypeReference<T>) type);
                } else if (type instanceof Class) {
                    result = convertResult(body, (Class<T>) type);
                }
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

    private static <T> T convertResult(String body, TypeReference<T> type) {
        if (body != null && type != null) {
            try {
                return mapper.readValue(body, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static <T> T convertResult(String body, Class<T> clazz) {
        if (body != null && clazz != null) {
            try {
                return mapper.readValue(body, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static OkHttpClient buildHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECT_TIME_OUT_IN_SEC, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT_IN_SEC, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_IN_SEC, TimeUnit.SECONDS).build();
    }

    private static Builder buildGetRequestBuilder(String url, Map<String, String> headers, Map<String, String> params) {
        String requestUrl = buildUrl(url, params);
        Builder rb = new Request.Builder().url(requestUrl);
        rb = buildHeaderJsonContent(rb);
        return buildHeader(rb, headers);
    }

    private static Builder buildPostRequestBuilder(String url, Map<String, String> headers, Object param) {
        Builder rb = new Request.Builder().url(url);
        rb = buildHeaderJsonContent(rb);
        rb = buildHeader(rb, headers);
        rb = buildBody(rb, param);
        return rb;
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

    private static Builder buildBody(Builder requestBuilder, Object param) {
        if (requestBuilder != null && param != null) {
            RequestBody body;
            try {
                body = RequestBody.create(JSON, mapper.writeValueAsString(param));
                requestBuilder = requestBuilder.post(body);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return requestBuilder;
    }

    public static void downloadFile(String url, Map<String, String> headers, Map<String, String> params, String saveFilename, DownloadListener listener) {
        OkHttpClient okHttpClient = buildHttpClient();
        Request request = buildGetRequestBuilder(url, headers, params).build();
        Call call = okHttpClient.newCall(request);
        executeDownloadSync(call, saveFilename, listener);
    }

    private static void executeDownloadSync(Call call, String saveFilename, DownloadListener listener) {
        Response response = null;
        InputStream is = null;
        OutputStream fos = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                is = response.body().byteStream();
                long sum = 0, total = response.body().contentLength();
                fos = new FileOutputStream(saveFilename);
                byte[] buf = new byte[2048];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    // 下载中
                    listener.onDownloading((int) (sum * 1.0f / total * 100));
                }
                fos.flush();
                // 下载完成
                listener.onDownloadSuccess();
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onDownloadFailed();
        } finally {
            if (response != null) {
                response.close();
            }
            IOUtil.closeSilently(is);
            IOUtil.closeSilently(fos);
        }
    }

    private static void executeDownloadAsync(Call call, String saveFilename, DownloadListener listener) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                OutputStream fos = null;
                is = response.body().byteStream();
                long sum = 0, total = response.body().contentLength();
                fos = new FileOutputStream(saveFilename);
                byte[] buf = new byte[2048];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    // 下载中
                    listener.onDownloading((int) (sum * 1.0f / total * 100));
                }
                fos.flush();
                // 下载完成
                listener.onDownloadSuccess();
            }
        });
    }

    /**
     * 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
