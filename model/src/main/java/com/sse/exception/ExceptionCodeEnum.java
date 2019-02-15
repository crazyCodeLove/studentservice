package com.sse.exception;

/**
 * <p>
 * 异常错误码汇总
 * </p>
 * author pczhao <br/>
 * date 2019-01-18 10:24
 */

public enum ExceptionCodeEnum {
    SUCCESS(200, "success"),
    RUNTIME_EXCEPTION(500, "RuntimeException，The engineer is rushing to repair it. Please try again later..."),
    RT_EXCEPTION(900, "custom exception base class"),
    EXCEPTION_CODE_NOT_FOUND(901, " no code corresponding to this exception"),
    PAGE_SIZE_OVERFLOW_EXCEPTION(902, " the number of single page exceeds the maximum"),

    PARAM_RT_EXCEPTION(1000, "param exception"),
    PARAM_NULL_EXCEPTION(1001, "param is null"),
    REQUEST_ATTRIBUTE_NULL_EXCEPTION(1200, "request properties null"),

    JWT_PARSE_EXCEPTION(2000, "JWT parse exception"),
    TOKEN_EXPIRE_EXCEPTION(2001, "token expired"),
    USER_PARSE_EXCEPTION(2002, "user parse exception in token"),

    USER_EXCEPTION(3000, "user exception"),
    USER_EXIST_EXCEPTION(3001, "user exist"),
    USER_NOT_EXIST_EXCEPTION(3002, "user not exist");

    private int code;
    private String note;

    ExceptionCodeEnum(int code, String message) {
        this.code = code;
        this.note = message;
    }

    public static ExceptionCodeEnum getExceptionEnumByCode(int code) {
        for (ExceptionCodeEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new ExceptionCodeNotFound(code + " exception code not found in ExceptionCodeEnum");
    }

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
