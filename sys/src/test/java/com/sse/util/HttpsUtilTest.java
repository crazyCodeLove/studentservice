package com.sse.util;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-06-17 13:16
 */

public class HttpsUtilTest {
    @Test
    public void httpsPostTest() {
        String url = "https://free-api.heweather.com/s6/air/now?location=beijing&key=f464c53cb02240a194640685ee425116";
        String json = "{}";
        HttpUtil.postForTypeHttps(url,new HashMap<>(),json,null);
    }
}
