package com.sse.exception.token;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;

/**
 * author pczhao <br/>
 * date 2019-01-17 13:18
 */

public class TokenExpireException extends RTException {

    public TokenExpireException(String message) {
        super(ExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION, message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(ExceptionCodeEnum.TOKEN_EXPIRE_EXCEPTION, message, cause);
    }

    public TokenExpireException(ExceptionCodeEnum codeEnum, String message) {
        super(codeEnum, message);
    }

    public TokenExpireException(ExceptionCodeEnum codeEnum, String message, Throwable cause) {
        super(codeEnum, message, cause);
    }
}
