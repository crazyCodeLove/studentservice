package com.sse.model.param;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 9:31
 */

public interface ParamBase {
    void preHandle();
    boolean validateParam();
}
