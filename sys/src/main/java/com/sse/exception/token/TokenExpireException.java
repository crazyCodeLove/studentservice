package com.sse.exception.token;

import com.sse.exception.RTExceptionBase;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 13:18
 */

public class TokenExpireException extends RTExceptionBase {
    public TokenExpireException() {
        super();
    }

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpireException(Throwable cause) {
        super(cause);
    }

    protected TokenExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
