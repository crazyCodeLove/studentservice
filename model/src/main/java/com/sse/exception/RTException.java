package com.sse.exception;

/**
 * author ZHAOPENGCHENG
 * date 2018-12-13 20:38
 */

public class RTException extends RuntimeException {

    protected Integer code;

    public RTException(String message) {
        super(message);
        this.code = ExceptionCodeEnum.RT_EXCEPTION.getCode();
    }

    public RTException(String message, Throwable cause) {
        super(message, cause);
        this.code = ExceptionCodeEnum.RT_EXCEPTION.getCode();
    }

    /**
     * 以下是为了方便复用，抛出不同 code 的同类异常
     */

    public RTException(ExceptionCodeEnum codeEnum, String message) {
        super(message);
        this.code = codeEnum.getCode();
    }

    public RTException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(message, cause);
        this.code = codeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
