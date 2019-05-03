package com.sse.util;

import java.io.*;

/**
 * @author pczhao
 * @email
 * @date 2018-10-11 14:09
 */

public class IOUtil {

    public static void closeSilently(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ioe) {
            ;
        }
    }

    public static void closeSilently(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioe) {
            ;
        }
    }

    public static void closeSilently(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException ioe) {
            ;
        }
    }

    public static void closeSilently(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException ioe) {
            ;
        }
    }

    public static void copy(InputStream in, OutputStream out) {
        byte[] buf = new byte[2048];
        int bytesRead;
        try {
            while ((bytesRead = in.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
