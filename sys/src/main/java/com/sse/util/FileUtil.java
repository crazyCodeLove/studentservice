package com.sse.util;

import cn.hutool.core.io.IORuntimeException;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author pczhao
 * @email
 * @date 2018-10-11 17:13
 */

public class FileUtil {

    public static String getPath(String filename) {
        String path = null;
        File file = new File(filename);
        if (file.isFile()) {
            path = file.getParent();
        } else if (file.isDirectory()) {
            path = filename;
        }
        return path;
    }

    public static String getFilename(String filename) {
        File file = new File(filename);
        return file.getName();
    }

    /**
     * 读取文件内容，返回文件字符串
     *
     * @param filename
     * @return
     */
    public static String getFileContentStr(final String filename) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-8")));
            sb = new StringBuilder(100);
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static byte[] getFileContentByte(final String filename) {
        long len = new File(filename).length();
        if (len >= Integer.MAX_VALUE) {
            throw new IORuntimeException("File is larger then max array size");
        }
        byte[] buf = new byte[2048];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /**
     * 将内容写到文件中，文件已存在就追加。否则就新建。
     *
     * @param filename
     * @param content
     * @return 成功返回 true，否则返回 false
     */
    public static boolean appendStr2File(final String filename, final String content) {
        boolean result = false;
        File file = new File(filename);
        BufferedWriter writer = null;
        if (file.exists()) {
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (writer != null) {
            try {
                writer.write(content);
                writer.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将字符串写到文件中。如果文件已存在就删除再写入；
     *
     * @param filename
     * @param content
     * @return
     */
    public static boolean writeStr2File(final String filename, final String content) {
        boolean result = false;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            writer.write("");
            writer.flush();
            writer.write(content);
            writer.close();
            result = true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean writeByte2File(String filename, byte[] content) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(content);
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteFile(String filename) {
        boolean result = false;
        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            file.delete();
            result = true;
        }
        return result;
    }

    /**
     * 获取系统临时目录
     */
    public static String getSystemTempDir() {
        return System.getProperty("java.io.tmpdir");
    }
}
