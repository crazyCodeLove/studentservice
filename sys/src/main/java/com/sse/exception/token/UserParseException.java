package com.sse.exception.token;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 13:22
 */

public class UserParseException extends RTException {

    public UserParseException(String message) {
        super(ExceptionCodeEnum.USER_PARSE_EXCEPTION, message);
    }

    public UserParseException(String message, Throwable cause) {
        super(ExceptionCodeEnum.USER_PARSE_EXCEPTION, message, cause);
    }

    public UserParseException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public UserParseException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
