package com.sse.exception.user;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * @author pczhao
 * @email
 * @date 2019-01-21 18:36
 */

public class UserException extends RTException {
    public UserException(String message) {
        super(ExceptionCodeEnum.USER_EXCEPTION, message);
    }

    public UserException(String message, Throwable cause) {
        super(ExceptionCodeEnum.USER_EXCEPTION, message, cause);
    }

    public UserException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public UserException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
