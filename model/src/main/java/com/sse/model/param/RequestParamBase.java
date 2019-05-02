package com.sse.model.param;

/**
 * author pczhao <br/>
 * date 2018-12-26 11:18
 */

public abstract class RequestParamBase {

    /**
     * 逻辑校验
     */
    public abstract void validParamInParam();

    /**
     * 请求参数预处理
     */
    public abstract void preHandle();
}
