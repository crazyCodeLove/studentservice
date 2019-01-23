package com.sse.exception.user;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * author ZHAOPENGCHENG  <br/>
 * date  2019-01-20 10:57
 */

public class UserNotExistException extends UserException {
    public UserNotExistException(String message) {
        super(ExceptionCodeEnum.USER_NOT_EXIST_EXCEPTION, message);
    }

    public UserNotExistException(String message, Throwable cause) {
        super(ExceptionCodeEnum.USER_NOT_EXIST_EXCEPTION, message, cause);
    }

    public UserNotExistException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public UserNotExistException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
