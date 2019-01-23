package com.sse.model.param;

/**
 * author pczhao <br/>
 * date 2019-01-17 9:31
 */

public interface ParamBase {
    void preHandle();
    boolean validateParam();
}
