package com.sse.model;

import com.sse.exception.ExceptionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * author ZHAOPENGCHENG <br/>
 * date 2018-12-13 20:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResultHolder<T> {

    private String reqId;
    /**
     * 响应状态码
     */
    protected Integer code;

    /**
     * 响应消息
     */
    protected String message;

    /**
     * 响应时间
     */
    protected Date responseTime;

    /**
     * 处理时间，单位毫秒
     */
    protected Long durationInMs;

    /**
     * 持有的响应结果
     */
    protected T result;

    /**
     * 包装响应结果并返回
     *
     * @param result 响应结果
     * @param <T>    结果类型
     */
    public static <T> ResponseResultHolder<T> setResult(T result) {
        ResponseResultHolder<T> response = new ResponseResultHolder<T>();
        response.result = result;
        return response;
    }

    /**
     * 无结果响应
     */
    public static ResponseResultHolder ok() {
        return new ResponseResultHolder();
    }

    /**
     * 包装异常的响应
     *
     * @param code 异常状态码
     * @param msg  异常消息
     */
    public static ResponseResultHolder error(int code, String msg) {
        return ResponseResultHolder.builder().code(code).message(msg).build();
    }

    /**
     * 包装异常的响应
     *
     * @param e 异常信息
     */
    public static ResponseResultHolder error(ExceptionCodeEnum e) {
        return ResponseResultHolder.builder().code(e.getCode()).message(e.getNote()).build();
    }

    /**
     * 设置响应状态
     *
     * @param code 响应状态吗
     * @param msg  响应信息
     */
    public void setResponseStatus(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * 设置响应时间信息
     *
     * @param responseTime 响应时间
     * @param duration     处理时长
     */
    public void setResponseTime(Date responseTime, long duration) {
        this.responseTime = responseTime;
        this.durationInMs = duration;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
