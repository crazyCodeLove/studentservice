package com.sse.exception;

/**
 * @author pczhao
 * @email
 * @date 2019-01-18 18:04
 */

public class ExceptionCodeNotFound extends RTException{
    public ExceptionCodeNotFound(String message) {
        super(ExceptionCodeEnum.EXCEPTION_CODE_NOT_FOUND, message);
    }

    public ExceptionCodeNotFound(String message, Throwable cause) {
        super(ExceptionCodeEnum.EXCEPTION_CODE_NOT_FOUND, message, cause);
    }

    public ExceptionCodeNotFound(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public ExceptionCodeNotFound(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
