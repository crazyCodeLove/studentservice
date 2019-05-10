package com.sse.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.sse.easyexcel.listener.ExcelListener;
import com.sse.easyexcel.model.ReadModel;
import com.sse.util.FileUtil;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-10 14:20
 */

public class ExcelReadTest {
    /**
     * 07版本excel读数据量少于1千行数据自动转成javamodel，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadJavaModelV2007() throws IOException {
        String filename = "D:\\logs\\000300perf.xlsx";
        InputStream inputStream = new FileInputStream(filename);
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, ReadModel.class));
        inputStream.close();
        System.out.println(data);
    }

    /**
     * 07版本excel读数据量大于1千行，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadListStringV2007() throws IOException {
        String filename = "D:\\logs\\000300perf.xlsx";
        InputStream inputStream = new FileInputStream(filename);
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, ReadModel.class), excelListener);
        inputStream.close();
    }

    /**
     * 03版本excel读数据量少于1千行数据转成javamodel，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void simpleReadJavaModelV2003() throws IOException {
        String filename = "D:\\000300perf.xls";
        InputStream inputStream = FileUtil.getInputStream(filename);
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, ReadModel.class));
        inputStream.close();
        System.out.println(data);
    }

    /**
     * 03版本excel读数据量大于1千行数据，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */
    @Test
    public void saxReadListStringV2003() throws IOException {
        String filename = "D:\\logs\\000300perf.xls";
        InputStream inputStream = FileUtil.getInputStream(filename);
        ExcelListener excelListener = new ExcelListener();
        EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, ReadModel.class), excelListener);
        inputStream.close();
    }
}
