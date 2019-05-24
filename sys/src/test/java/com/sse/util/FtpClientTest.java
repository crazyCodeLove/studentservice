package com.sse.util;

import com.sse.util.ftp.FtpClientFactory;
import com.sse.util.ftp.FtpClientHelper;
import com.sse.util.ftp.FtpPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-24 11:04
 */

@Slf4j
public class FtpClientTest {
    private FtpPoolConfig config;
    private FtpClientHelper ftpClient;

    @Before
    public void init() {
        String host = "127.0.0.1";//主机名
        String username = "user1";//用户名
        String password = "123456Sse";//密码
        config = new FtpPoolConfig();
        config.setHost(host);
        config.setUsername(username);
        config.setPassword(password);
        ftpClient = new FtpClientHelper(config);
    }

    @Test
    public void addDirectoryTest() throws Exception {
        String path = "中国目录";
        ftpClient.makeDirectory(path);
    }

    @Test
    public void uploadFileTest() throws Exception {
        String remotePath = "work1/demo1.txt";
        String localPath = "D:\\logs\\controller.log";
        FileInputStream inputStream = new FileInputStream(localPath);
        ftpClient.uploadFile(remotePath, inputStream);
    }

    @Test
    public void listNamesTest() throws Exception {
        String remotePath = null;
        String[] names = ftpClient.listNames(remotePath);
        System.out.println(Arrays.asList(names));
    }

    @Test
    public void pathExistTest() throws Exception {
        String path = "work1";
        boolean exist = ftpClient.pathExist(path);
        System.out.println(path + " exist:" + exist);
    }

    @Test
    public void listFileTest() throws Exception {
        String path = "work";
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        System.out.println("total size:" + ftpFiles.length);
        String filename;
        for (FTPFile f : ftpFiles) {
            filename = new String(f.getName().getBytes(FtpClientFactory.SERVER_CHARSET), FtpClientFactory.LOCAL_CHARSET);
            log.info("filename: {}, isFile: {}, size: {}(KB)", filename, f.isFile(), (f.getSize() >> 10));
        }
    }

}
