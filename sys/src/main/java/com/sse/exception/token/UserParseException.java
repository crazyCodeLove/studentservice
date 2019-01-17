package com.sse.exception.token;

import com.sse.exception.RTExceptionBase;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 13:22
 */

public class UserParseException extends RTExceptionBase {
    public UserParseException() {
        super();
    }

    public UserParseException(String message) {
        super(message);
    }

    public UserParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserParseException(Throwable cause) {
        super(cause);
    }

    protected UserParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
