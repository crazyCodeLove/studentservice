package com.sse.exception;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-22 19:37
 */

public class PageSizeOverFlowException extends RTException {
    public PageSizeOverFlowException(String message) {
        super(ExceptionCodeEnum.PAGE_SIZE_OVERFLOW_EXCEPTION, message);
    }

    public PageSizeOverFlowException(String message, Throwable cause) {
        super(ExceptionCodeEnum.PAGE_SIZE_OVERFLOW_EXCEPTION, message, cause);
    }

    public PageSizeOverFlowException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public PageSizeOverFlowException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
