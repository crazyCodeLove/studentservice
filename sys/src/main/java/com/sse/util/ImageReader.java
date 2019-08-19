package com.sse.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <p></p>
 * author pczhao<br/>
 * date  2019-08-06 15:09
 */

public class ImageReader {

    public static void fun1() {
        String filename = "D:\\logs\\temp\\test.tif";
        try {
            BufferedImage bufferedImage = ImageIO.read(FileUtil.getInputStream(filename));
            if (bufferedImage == null) {
                System.out.println("image read null");
            } else {
                System.out.println("read success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fun2() {
        String filename = "D:\\logs\\temp\\test.tif";
        String destFilename = "D:\\logs\\temp\\testtif.png";
        try {
            File out = new File(destFilename);
            BufferedImage bufferedImage = ImageIO.read(FileUtil.getInputStream(filename));
            ImageIO.write(bufferedImage, "png", out);
            if (bufferedImage == null) {
                System.out.println("image read null");
            } else {
                System.out.println("read success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        fun2();
    }
}
