package com.sse.exception;

import org.junit.Test;

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
}
