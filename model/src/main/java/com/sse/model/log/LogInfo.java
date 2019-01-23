package com.sse.model.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author pczhao <br/>
 * date 2019-01-18 14:30
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo {
    //>>>>>>>>>>>>>> 一般的请求信息 */

    /**
     * 请求 url
     */
    private String url;

    /**
     * 请求方法 post/get/put/delete
     */
    private String method;

    /**
     * get 请求参数
     */
    private String queryString;

    /**
     * 请求的地址
     */
    private String ip;

    //>>>>>>>>>>>>>>>> 请求方法信息

    /**
     * 处理类
     */
    private String callClass;

    /**
     * 处理方法
     */
    private String callMethod;

    /**
     * 处理方法的入参
     */
    private String params;

    //>>>>>>>>>>>>>>>>>>> 响应信息

    /**
     * 响应结果
     */
    private String result;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 处理时间，单位 ms
     */
    private long duration;

    public void setResponseTime(Date responseTime, long duration) {
        this.responseTime = responseTime;
        this.duration = duration;
    }

    public void setResponseStatus(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }


}
