package com.sse.util;

import com.google.common.base.Charsets;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Random;

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
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            outputStream.write("nice to meet you".getBytes());
        }
        outputStream.close();
        System.out.println("finished. cost time:" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void appendByteToFileTest() {
        String filename = "D:\\logs\\testnio1.txt";
        String content = "0";
        FileUtil.appendByteToFileNio(filename, content.getBytes(Charsets.UTF_8));
    }

    @Test
    public void getFileContentByteNioTest() {
        String filename = "D:\\logs\\testnio1.txt";
        byte[] fileContent = FileUtil.getFileContentByteNio(filename);
        System.out.println(new String(fileContent));
    }

    @Test
    public void agzhzTest() {
        Random random = new Random();
        String filename = "D:\\logs\\agzhzTest1.txt";
        int totalNum = 200000000;
//        int totalNum = 10;

        int groupIndex = 1;
        String line = "";
        StringBuilder sb = new StringBuilder(1000000);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < totalNum; i++) {
            // 同一账户组，账户
            line = formatAGZHZ(groupIndex, i + 1);
            sb.append(line);
            if ((i + 1) % 100000 == 0) {
                FileUtil.appendByteToFileNio(filename, sb.toString().getBytes(Charset.forName("GB18030")));
                sb = new StringBuilder(1000000);
            }
            if (random.nextBoolean()) {
                groupIndex++;
            }
        }
        if (sb.length() > 0) {
            FileUtil.appendByteToFileNio(filename, sb.toString().getBytes(Charset.forName("GB18030")));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("cost time: ms " + (endTime - startTime));
    }

    @Test
    public void agzhzNewTest() throws FileNotFoundException {
        Random random = new Random();
        String filename = "D:\\logs\\agzhzTest2.txt";
        int totalNum = 100000000;
//        int totalNum = 10;

        int groupIndex = 1;
        String line = "";
        StringBuilder sb = new StringBuilder(10000000);
        FileOutputStream outputStream = new FileOutputStream(filename, true);
        FileChannel channel = outputStream.getChannel();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < totalNum; i++) {
            // 同一账户组，账户
            line = formatAGZHZ(groupIndex, i + 1);
            sb.append(line);
            if ((i + 1) % 500000 == 0) {
                FileUtil.appendByteToFileNio(channel, sb.toString().getBytes(Charset.forName("GB18030")));
                sb = new StringBuilder(10000000);
            }
            if (random.nextBoolean()) {
                groupIndex++;
            }
        }
        if (sb.length() > 0) {
            FileUtil.appendByteToFileNio(channel, sb.toString().getBytes(Charset.forName("GB18030")));
        }
        IOUtil.closeSilently(channel);
        IOUtil.closeSilently(outputStream);
        long endTime = System.currentTimeMillis();
        System.out.println("cost time: ms " + (endTime - startTime));
    }

    public static String formatAGZHZ(int groupIndex, int accountNo) {
        return String.format("%10d|A%09d\n", groupIndex, accountNo);
    }


}
