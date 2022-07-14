package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author ZHAOPENGCHENG  <br/>
 * date 2018-12-13 20:45
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestParamHolder<T> {
    private String reqId;
    private T param;

    public static <T> RequestParamHolder<T> setRequestParam(T t) {
        RequestParamHolder<T> param = new RequestParamHolder<T>();
        param.setParam(t);
        return param;
    }
}
