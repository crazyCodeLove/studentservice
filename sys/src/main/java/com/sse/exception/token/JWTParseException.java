package com.sse.exception.token;

import com.sse.exception.RTExceptionBase;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 13:19
 */

public class JWTParseException extends RTExceptionBase {
    public JWTParseException() {
        super();
    }

    public JWTParseException(String message) {
        super(message);
    }

    public JWTParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTParseException(Throwable cause) {
        super(cause);
    }

    protected JWTParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
