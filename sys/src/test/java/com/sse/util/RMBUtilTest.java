package com.sse.util;

import org.junit.Test;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 13:57
 */

public class RMBUtilTest {

    @Test
    public void positiveIntegerToHanStrTest() {
        double num = 1245.0315;
        String result = RMBUtil.numToRMBStr(num);
        System.out.println(result);
    }
}
