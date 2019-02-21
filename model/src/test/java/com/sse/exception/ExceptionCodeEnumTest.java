package com.sse.exception;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-19 9:12
 */

public class ExceptionCodeEnumTest {

    @Test
    public void outTest() {
        ExceptionCodeEnum codeEnum = ExceptionCodeEnum.PARAM_RT_EXCEPTION;
        System.out.println(codeEnum);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getExceptionEnumByCodeTest() {
        int code = 10003000;
        ExceptionCodeEnum exceptionEnum = ExceptionCodeEnum.getExceptionEnumByCode(code);
        Assert.assertEquals(exceptionEnum, ExceptionCodeEnum.USER_EXCEPTION);
        code--;
        expectedException.expect(ExceptionCodeNotFound.class);
        expectedException.expectMessage("not found in ExceptionCodeEnum");
        exceptionEnum = ExceptionCodeEnum.getExceptionEnumByCode(code);
        System.out.println(exceptionEnum);
    }
}
