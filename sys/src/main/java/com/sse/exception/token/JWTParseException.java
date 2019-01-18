package com.sse.exception.token;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * author pczhao
 * date 2019-01-17 13:19
 */

public class JWTParseException extends RTException {

    public JWTParseException(String message) {
        super(ExceptionCodeEnum.JWT_PARSE_EXCEPTION, message);
    }

    public JWTParseException(String message, Throwable cause) {
        super(ExceptionCodeEnum.JWT_PARSE_EXCEPTION, message, cause);
    }

    public JWTParseException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public JWTParseException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
