package com.sse.config.exception;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.ParamRTException;

/**
 * author pczhao
 * date 2018-12-26 12:30
 */

public class ParamNullException extends ParamRTException {

    public ParamNullException(String message) {
        super(ExceptionCodeEnum.PARAM_NULL_EXCEPTION, message);
    }

    public ParamNullException(String message, Throwable cause) {
        super(ExceptionCodeEnum.PARAM_NULL_EXCEPTION, message, cause);
    }

    public ParamNullException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public ParamNullException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
