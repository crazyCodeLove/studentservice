package com.sse.util;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @Test
    public void writeFileTest() throws IOException {
        long startTime = System.currentTimeMillis();
        String path = "D:\\logs\\temp1.txt";
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        for (int i=0;i<Integer.MAX_VALUE;i++) {
            outputStream.write("nice to meet you".getBytes());
        }
        outputStream.close();
        System.out.println("finished. cost time:" + (System.currentTimeMillis() - startTime));
    }
}
