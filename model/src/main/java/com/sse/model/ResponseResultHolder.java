package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-13 20:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResultHolder<T> {
    /**
     * 响应时间
     */
    protected Date responseTime;
    /**
     * 处理时间，单位毫秒
     */
    protected Long duration;

    protected T result;
    private ResponseError error;

    /**
     * 包装响应结果并返回
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResultHolder<T> setResult(T result) {
        ResponseResultHolder<T> response = new ResponseResultHolder<>();
        response.result = result;
        return response;
    }

    /**
     * 无结果响应
     *
     * @return
     */
    public static ResponseResultHolder ok() {
        return new ResponseResultHolder();
    }

    /**
     * 包装异常的响应
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResponseResultHolder error(int code, String msg) {
        ResponseResultHolder res = new ResponseResultHolder();
        res.setError(ResponseError.builder().code(code).message(msg).build());
        return res;
    }

    @Builder
    public static class ResponseError {
        private Integer code;
        private String message;

        public ResponseError(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public ResponseError() {
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ResponseError{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

}
