package com.sse.exception.user;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * author ZHAOPENGCHENG
 * date  2019-01-20 10:57
 */

public class UserExistException extends UserException {
    public UserExistException(String message) {
        super(ExceptionCodeEnum.USER_EXIST_EXCEPTION, message);
    }

    public UserExistException(String message, Throwable cause) {
        super(ExceptionCodeEnum.USER_EXIST_EXCEPTION, message, cause);
    }

    public UserExistException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public UserExistException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
