package com.sse.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 异常错误码汇总
 * </p>
 * author pczhao <br/>
 * date 2019-01-18 10:24
 */

public enum ExceptionCodeEnum {
    SUCCESS(200, "success"),
    RUNTIME_EXCEPTION(10000000, "RuntimeException，The engineer is rushing to repair it. Please try again later..."),
    RT_EXCEPTION(10000900, "custom exception base class"),
    EXCEPTION_CODE_NOT_FOUND(10000901, " no code corresponding to this exception"),
    PAGE_SIZE_OVERFLOW_EXCEPTION(10000902, " the number of single page exceeds the maximum"),

    PARAM_RT_EXCEPTION(10001000, "param exception"),
    PARAM_NULL_EXCEPTION(10001001, "param is null"),
    REQUEST_ATTRIBUTE_NULL_EXCEPTION(10001200, "request properties null"),

    JWT_PARSE_EXCEPTION(10002000, "JWT parse exception"),
    TOKEN_EXPIRE_EXCEPTION(10002001, "token expired"),
    USER_PARSE_EXCEPTION(10002002, "user parse exception in token"),

    USER_EXCEPTION(10003000, "user exception"),
    USER_EXIST_EXCEPTION(10003001, "user exist"),
    USER_NOT_EXIST_EXCEPTION(10003002, "user not exist");

    private int code;
    private String note;

    ExceptionCodeEnum(int code, String message) {
        this.code = code;
        this.note = message;
    }

    private static Map<Integer, ExceptionCodeEnum> codeExceptionMap = new HashMap<Integer, ExceptionCodeEnum>(ExceptionCodeEnum.values().length);

    static {
        // codeExceptionMap 进行初始化
        for (ExceptionCodeEnum e : values()) {
            codeExceptionMap.put(e.getCode(), e);
        }
    }

    public static ExceptionCodeEnum getExceptionEnumByCode(int code) {
        if (!codeExceptionMap.containsKey(code)) {
            throw new ExceptionCodeNotFound("exception code [" + code + "] not found in ExceptionCodeEnum");
        }
        //返回 code 对应的 Exception
        return codeExceptionMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
