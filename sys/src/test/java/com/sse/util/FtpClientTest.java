package com.sse.util;

import com.sse.util.ftp.FtpClientHelper;
import com.sse.util.ftp.FtpPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.UUID;

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
        String host = "45.78.33.187";//主机名
        String username = "allen";//用户名
        String password = "1128zpc@";//密码
        config = new FtpPoolConfig();
        config.setHost(host);
        config.setUsername(username);
        config.setPassword(password);
        ftpClient = new FtpClientHelper(config);
    }

    @Test
    public void addDirectoryTest() throws Exception {
        String workDir = "work";
        log.info("current work directory: {}", ftpClient.getCurrentWorkingDirectory());
        if (!ftpClient.pathExist(workDir)) {
            ftpClient.makeDirectory(workDir);
        }
        ftpClient.setWorkingDirectory(workDir);
        log.info("change to work directory: {}", ftpClient.getCurrentWorkingDirectory());
        String path = UUID.randomUUID().toString();
        boolean result = ftpClient.makeDirectory(path);
        log.info("add directory: {}, result: {}", path, result);
        listNamesTest();
    }

    @Test
    public void deleteDirectoryTest() throws Exception {
        String path = "天天";
        boolean result = ftpClient.deleteFile(path);
        System.out.println("delete " + path + ":" + result);
        listNamesTest();
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
        System.out.println("size:" + names.length);
        for (String s : names) {
            System.out.println(s);
            System.out.println();
        }
    }

    @Test
    public void pathExistTest() throws Exception {
        String path = "work1";
        boolean exist = ftpClient.pathExist(path);
        System.out.println(path + " exist:" + exist);
    }

    @Test
    public void listFileTest() throws Exception {
        String path = null;
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        System.out.println("total size:" + ftpFiles.length);
        for (FTPFile f : ftpFiles) {
            log.info("filename: {}, isFile: {}, size: {}(KB)", f.getName(), f.isFile(), (f.getSize() >> 10));
        }
    }

}
