package com.sse.util;

import org.junit.Test;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-05-03 17:39
 */

public class FileUtilTest {

    @Test
    public void systemTempDir() {
        String fileName = FileUtil.getSystemTempDir();
        System.out.println(fileName);
    }
}
