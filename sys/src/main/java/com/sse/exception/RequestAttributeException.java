package com.sse.exception;

/**
 * author pczhao
 * date 2019-01-18 17:03
 */

public class RequestAttributeException extends RTException{

    public RequestAttributeException(String message) {
        super(ExceptionCodeEnum.REQUEST_ATTRIBUTE_NULL_EXCEPTION, message);
    }

    public RequestAttributeException(String message, Throwable cause) {
        super(ExceptionCodeEnum.REQUEST_ATTRIBUTE_NULL_EXCEPTION, message, cause);
    }

    public RequestAttributeException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public RequestAttributeException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
