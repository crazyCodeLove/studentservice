package com.sse.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * date  2019-03-01 10:57
 */

@Slf4j
public class ImageWatermarkUtil {
    // 水印透明度
    private static final float ALPHA = 0.26f;
    // 水印文字字体
    private static final int FOUNT_SIZE = 13;
    // 水印文字所占长度
    private static final int LINE_WIDTH = 210;
    private static final Font FONT = new Font("仿宋", Font.PLAIN, FOUNT_SIZE);
    // 水印文字颜色
    private static final Color COLOR = new Color(190, 190, 190);

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

    /**
     * 将 pdf 文件每页转换成png图片到 imageDirPath 路径中，文件名是 pdfPath 文件名
     *
     * @param pdfPath      pdf 文件名
     * @param imageDirPath 待保存图片路径
     */
    public static void convertPdf2Image(String pdfPath, String imageDirPath) {
        log.info("start convert pdf file:[{}] to image path:[{}]", pdfPath, imageDirPath);
        if (!new File(pdfPath).exists()) {
            log.info("pdfFilename:[{}] not exist", pdfPath);
            return;
        }
        if (!new File(imageDirPath).exists()) {
            log.info("imageDir:[{}] not exist", imageDirPath);
            return;
        }
        byte[] pdfContent = FileUtil.getFileContentByte(pdfPath);
        String filename = FileUtil.getFilename(pdfPath);
        float dpi = 200;
        convertPdf2Image(pdfContent, filename, imageDirPath, dpi);
        log.info("convert pdf file:[{}] to image success", filename);
    }

    /**
     * 将 pdf 内容 每页转换成png图片到 imageDirPath 路径中，文件名是 pdfPath 文件名
     *
     * @param pdfContent   pdf文件内容
     * @param pdfFilename  pdf 文件名
     * @param imageDirPath 待保存图片路径
     * @param dpi          转换成图片的清晰度
     */
    private static void convertPdf2Image(byte[] pdfContent, String pdfFilename, String imageDirPath, float dpi) {
        log.info("convert pdfFilename:[{}] to imageDir:[{}] with dpi:[{}]", pdfFilename, imageDirPath, dpi);
        if (ArrayUtils.isEmpty(pdfContent)) {
            return;
        }
        // 为了保证显示清除，至少 90
        if (dpi < 90) {
            dpi = 90;
        }
        String baseSir = imageDirPath;
        if (baseSir.endsWith("/") || baseSir.endsWith("\\")) {
            baseSir += pdfFilename + "_";
        } else {
            baseSir += File.separator + pdfFilename + "_";
        }
        PDDocument document = null;
        BufferedOutputStream outputStream = null;
        try {
            document = PDDocument.load(pdfContent);
            int pageCount = document.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            String imgPath;
            for (int i = 0; i < pageCount; i++) {
                imgPath = baseSir + i + ".png";
                outputStream = new BufferedOutputStream(new FileOutputStream(imgPath));
                BufferedImage image = pdfRenderer.renderImageWithDPI(i, dpi, ImageType.RGB);
                ImageIO.write(image, "png", outputStream);
                outputStream.close();
                log.info("convert to png, total[{}], now[{}], ori:[{}], des[{}]", pageCount, i + 1, pdfFilename, imgPath);
            }
        } catch (IOException e) {
            log.error("convert pdf to image error, pdfFilename:" + pdfFilename, e);
        } finally {
            IOUtil.closeSilently(outputStream);
            IOUtil.closeSilently(document);
        }
    }
}
