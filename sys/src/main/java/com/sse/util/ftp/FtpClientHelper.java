package com.sse.util.ftp;

import com.sse.util.IOUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 20:51
 */

@Slf4j
@Getter
public class FtpClientHelper {
    private FtpClientPool ftpClientPool;

    public FtpClientHelper(FtpClientPool ftpClientPool) {
        this.ftpClientPool = ftpClientPool;
    }

    public FtpClientHelper(FtpPoolConfig config) {
        FtpClientFactory ftpClientFactory = new FtpClientFactory(config);
        ftpClientPool = new FtpClientPool(ftpClientFactory);
    }

    /**
     * 下载 remote文件流
     *
     * @param remotePath 远程文件
     * @return 字节数据
     */
    public byte[] downloadFile(String remotePath) throws Exception {
        FTPClient client = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            client = ftpClientPool.getFtpClient();
            log.info("get download file input stream");
            inputStream = client.retrieveFileStream(remotePath);
            IOUtil.copy(inputStream, outputStream);
            return outputStream.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (!client.completePendingCommand()) {
                client.logout();
                client.disconnect();
                ftpClientPool.getPool().invalidateObject(client);
            }
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 上传文件
     */
    public boolean uploadFile(String remotePath, InputStream localInputStream) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            log.info("start upload file to {}", remotePath);
            return client.storeFile(remotePath, localInputStream);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 创建目录    单个不可递归
     *
     * @param remotePath 目录名称
     */
    public boolean makeDirectory(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.makeDirectory(remotePath);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 设置当前工作目录
     *
     * @param remotePath 远程工作目录
     */
    public boolean setWorkingDirectory(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.changeWorkingDirectory(remotePath);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 获取当前工作目录
     */
    public String getCurrentWorkingDirectory() throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.printWorkingDirectory();
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 删除目录，单个不可递归 (if empty).
     *
     * @param pathname 目录名
     */
    public boolean removeDirectory(String pathname) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.removeDirectory(pathname);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 列出目录下面的路径列表，如果 remotePath 为 null，则列出当前目录下的列表
     *
     * @param remotePath ftp 文件目录
     */
    public String[] listNames(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            if (remotePath != null) {
                return client.listNames(remotePath);
            }
            return client.listNames();
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 路径是否存在
     *
     * @param remotePath ftp 服务器路径
     */
    public boolean pathExist(String remotePath) throws Exception {
        String[] names = listNames(remotePath);
        return names != null && names.length >= 1;
    }

    /**
     * 删除文件 单个 ，不可递归
     *
     * @param pathname 文件名
     */
    public boolean deleteFile(String pathname) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.deleteFile(pathname);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }
}
