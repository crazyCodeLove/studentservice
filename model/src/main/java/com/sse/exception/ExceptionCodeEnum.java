package com.sse.exception;

/**
 * 异常错误码汇总
 * author pczhao
 * date 2019-01-18 10:24
 */

public enum ExceptionCodeEnum {
    SUCCESS(200, "请求成功"),
    RUNTIME_EXCEPTION(500, "运行时异常"),
    RT_EXCEPTION(900, "自定义异常基础类"),
    EXCEPTION_CODE_NOT_FOUND(901, "没有该异常对应的 code"),
    PARAM_RT_EXCEPTION(1000, "参数异常"),
    PARAM_NULL_EXCEPTION(1001, "参数为 null"),
    REQUEST_ATTRIBUTE_NULL_EXCEPTION(1200, "请求属性为 null"),
    JWT_PARSE_EXCEPTION(2000, "JWT 解析异常"),
    TOKEN_EXPIRE_EXCEPTION(2001, "token 过期"),
    USER_PARSE_EXCEPTION(2002, "token 中用户解析异常"),
    USER_EXCEPTION(3000, "用户异常"),
    USER_EXIST_EXCEPTION(3001, "用户已存在"),
    USER_NOT_EXIST_EXCEPTION(3002, "用户不存在"),
    USERNAME_CHANGE_EXCEPTION(3003, "用户名更改")


    ;

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
        throw new ExceptionCodeNotFound(code + " 没有在 ExceptionCodeEnum 中找到");
    }

    public int getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
