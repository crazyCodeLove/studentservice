package com.sse.util;

import cn.hutool.core.io.IORuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * author pczhao
 * date 2018-10-11 17:13
 */

@Slf4j
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
        } catch (IOException e) {
            log.error("get file content str error:" + filename, e);
        }
        return sb.toString();
    }

    public static byte[] getFileContentByte(final String filename) {
        log.info("get file:[{}] content byte[]", filename);
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
        } catch (IOException e) {
            log.error("get file:[" + filename + "] content error", e);
        }
        return outputStream.toByteArray();
    }

    public static InputStream getInputStream(final String filename) {
        log.info("get file:[{}] imputstream", filename);
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            log.error("get file [{}] inputStream error", filename);
        }
        return bufferedInputStream;
    }

    /**
     * 将内容写到文件中，文件已存在就追加。否则就新建。
     *
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteFile(String filename) {
        log.info("try to delete file:[{}]", filename);
        boolean result = false;
        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            result = file.delete();
            log.info("delete file success:[{}]", filename);
        }
        return result;
    }

    /**
     * 获取系统临时目录
     */
    public static String getSystemTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    public static void appendByteToFileNio(final String filename, byte[] content) {
        if (content == null || content.length == 0) {
            return;
        }
        int buffLength = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(buffLength);
        FileOutputStream outputStream = null;
        FileChannel channel = null;
        try {
            outputStream = new FileOutputStream(filename, true);
            channel = outputStream.getChannel();
            int startIndex = 0;
            int length;
            while (startIndex < content.length) {
                length = startIndex + buffLength <= content.length ? buffLength : content.length - startIndex;
                buffer.put(content, startIndex, length);
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
                startIndex += length;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtil.closeSilently(channel);
            IOUtil.closeSilently(outputStream);
        }
    }

    public static void appendByteToFileNio(final FileChannel channel, byte[] content) {
        if (content == null || content.length == 0) {
            return;
        }
        int buffLength = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(buffLength);
        int startIndex = 0;
        int length;
        while (startIndex < content.length) {
            length = startIndex + buffLength <= content.length ? buffLength : content.length - startIndex;
            buffer.put(content, startIndex, length);
            buffer.flip();
            try {
                channel.write(buffer);
            } catch (IOException e) {
                log.info(e.getMessage(), e);
            }
            buffer.clear();
            startIndex += length;
        }
    }

    public static byte[] getFileContentByteNio(final String filename) {
        File file = new File(filename);
        if (file.length() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("file is to large");
        }
        byte[] result = new byte[(int) file.length()];
        if (file.length() == 0) {
            return result;
        }
        int startIndex = 0;
        FileInputStream inputStream = null;
        FileChannel channel = null;
        try {
            inputStream = new FileInputStream(file);
            channel = inputStream.getChannel();
            int buffLength = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(buffLength);
            while (startIndex < result.length) {
                channel.read(buffer);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    result[startIndex++] = buffer.get();
                }
                buffer.clear();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtil.closeSilently(channel);
            IOUtil.closeSilently(inputStream);
        }
        return result;
    }


}
