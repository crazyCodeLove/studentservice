package com.sse.util;


import org.junit.jupiter.api.Test;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-08-17 13:55
 */

public class ImageWatermarkUtilTest {

    @Test
    public void convert2imageTest() {
//        String pdfFilename = "D:\\logs\\temp\\000208.pdf";
//        String pdfFilename = "D:\\logs\\temp\\001659p14.pdf";
//        String pdfFilename = "D:\\logs\\temp\\maindoc.pdf";
//        String pdfFilename = "D:\\logs\\temp\\573636.pdf";
        String pdfFilename = "D:\\logs\\temp\\069020.pdf";
        String imageDirPath = "D:\\logs\\temp\\image";
        ImageWatermarkUtil.convertPdf2Image(pdfFilename,imageDirPath);
    }
}
