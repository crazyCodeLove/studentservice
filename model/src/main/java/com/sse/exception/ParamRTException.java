package com.sse.exception;

/**
 * author ZHAOPENGCHENG
 * date 2018-12-13 20:39
 */

public class ParamRTException extends RTException {

    public ParamRTException(String message) {
        super(ExceptionCodeEnum.PARAM_RT_EXCEPTION, message);
    }

    public ParamRTException(String message, Throwable cause) {
        super(ExceptionCodeEnum.PARAM_RT_EXCEPTION, message, cause);
    }

    public ParamRTException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public ParamRTException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
