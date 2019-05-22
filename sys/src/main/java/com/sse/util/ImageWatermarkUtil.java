package com.sse.util;


import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * date  2019-03-01 10:57
 */

public class ImageWatermarkUtil {
    // 水印透明度
    private static final float ALPHA = 0.26f;
    // 水印文字字体
    private static final int FOUNT_SIZE = 13;
    // 水印文字所占长度
    private static final int LINE_WIDTH = 210;
    private static final Font FONT = new Font("仿宋", Font.PLAIN, FOUNT_SIZE);
    // 水印文字颜色
    private static final Color COLOR = new Color(190,190,190);

    /**
     * 将图片内容(byte[])转换成 BufferedImage
     */
    public static BufferedImage bytes2BufferedImage(byte[] content) {
        BufferedImage result = null;
        if (content != null && content.length > 0) {
            try {
                result = ImageIO.read(new ByteArrayInputStream(content));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将图片转换成字节数组
     */
    public static byte[] bufferedImage2Bytes(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (image != null) {
            try {
                ImageIO.write(image, "png", outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    public static BufferedImage addWatermark(BufferedImage bfimage, String watermark) {
        if (StringUtils.isBlank(watermark)) {
            return bfimage;
        }
        final int SEP = 75;
        final int LINE_COUNT = 2 * bfimage.getHeight() / SEP;
        final int WORD_REPEAT_COUNT = (int) (1.5 * bfimage.getHeight() / (watermark.length() * FOUNT_SIZE));
        Graphics2D g = bfimage.createGraphics();
        setWatermarkStyle(g);
        //添加水印
        String repeatWatermark = repeatStr(watermark, WORD_REPEAT_COUNT);
        for (int i = 1; i < LINE_COUNT; i++) {
            g.drawString(repeatWatermark, -SEP * (LINE_COUNT / 2 - Math.abs(i - LINE_COUNT / 2)), SEP * i);
        }
        // 9、释放资源
        g.dispose();
        return bfimage;
    }

    private static String repeatStr(String str, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(str);
            int blankCount = (LINE_WIDTH - str.length() * FOUNT_SIZE) / FOUNT_SIZE;
            for (int j = 0; j < blankCount; j++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private static void setWatermarkStyle(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // 5、设置水印文字颜色
        graphics.setColor(COLOR);
        // 6、设置水印文字Font
        graphics.setFont(FONT);
        // 7、设置水印文字透明度
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
        //设置旋转
        graphics.rotate(-Math.PI * 0.21);
    }
}
