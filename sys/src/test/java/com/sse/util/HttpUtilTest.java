package com.sse.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.user.User;
import com.sse.model.user.UserSaveParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-04-27 12:27
 */

@Slf4j
public class HttpUtilTest {

    @Test
    public void postClassTest() {
        String url = "http://localhost:8060/student-service-provider/user";
        Map<String, String> headers = new HashMap<>();
        RequestParamHolder<UserSaveParam> param = RequestParamHolder.<UserSaveParam>builder().param(UserSaveParam.builder().username("test1").password("pass1").build()).build();
        ResponseResultHolder result = HttpUtil.postForType(url, headers, param, ResponseResultHolder.class);
        log.info("response success. result: {}", result);
    }

    @Test
    public void postTypeTest() {
        String url = "http://localhost:8060/student-service-provider/user";
        Map<String, String> headers = new HashMap<>();
        RequestParamHolder<UserSaveParam> param = RequestParamHolder.<UserSaveParam>builder().param(UserSaveParam.builder().username("test2").password("pass2").build()).build();
        ResponseResultHolder<User> resultHolder = HttpUtil.postForType(url, headers, param, new TypeReference<ResponseResultHolder<User>>() {
        });
        log.info("response success. resultHolder: {}, result: {}", resultHolder, resultHolder.getResult());
    }

    @Test
    public void downloadTest() {
        String url = "http://www.csindex.com.cn/uploads/file/autofile/cons/000001cons.xls";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        String saveFilename = "D:\\logs\\000001cons.xls";
        HttpUtil.downloadFile(url, headers, params, saveFilename, null);
    }
}
