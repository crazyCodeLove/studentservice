package com.sse.exception;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-13 20:38
 */

public class RTBaseException extends RuntimeException {

    public RTBaseException() {
        super();
    }

    public RTBaseException(String message) {
        super(message);
    }

    public RTBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RTBaseException(Throwable cause) {
        super(cause);
    }

    protected RTBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
