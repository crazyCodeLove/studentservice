package com.sse.util.ftp;

import com.sse.util.IOUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

/**
 * todo:中文编码问题
 * 英文和数字没有问题
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
            inputStream = client.retrieveFileStream(toFtpServerCharsetFormat(remotePath));
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

    public void downloadFile(String remotePath, String localPath) throws Exception {
        FTPClient client = null;
        InputStream inputStream = null;
        OutputStream outputStream = new FileOutputStream(localPath);
        try {
            client = ftpClientPool.getFtpClient();
            log.info("get download file input stream");
            inputStream = client.retrieveFileStream(toFtpServerCharsetFormat(remotePath));
            IOUtil.copy(inputStream, outputStream);
        } finally {
            IOUtil.closeSilently(inputStream);
            IOUtil.closeSilently(outputStream);
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
            return client.storeFile(toFtpServerCharsetFormat(remotePath), localInputStream);
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
            return client.makeDirectory(toFtpServerCharsetFormat(remotePath));
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
            return client.changeWorkingDirectory(toFtpServerCharsetFormat(remotePath));
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
            String workingDirectory = client.printWorkingDirectory();
            return toLocalCharsetFormat(workingDirectory);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 删除目录，单个不可递归 (if empty).
     *
     * @param remotePath 目录名
     */
    public boolean removeDirectory(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.removeDirectory(toFtpServerCharsetFormat(remotePath));
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
                return client.listNames(toFtpServerCharsetFormat(remotePath));
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
     * 列出目录中所有文件
     *
     * @param remotePath ftp 服务器目录
     */
    public FTPFile[] listFiles(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            if (remotePath != null) {
                return client.listFiles(toFtpServerCharsetFormat(remotePath));
            }
            return client.listFiles();
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 删除文件 单个 ，不可递归
     *
     * @param remotePath 文件名
     */
    public boolean deleteFile(String remotePath) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.deleteFile(toFtpServerCharsetFormat(remotePath));
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    private String toFtpServerCharsetFormat(String remotePath) throws UnsupportedEncodingException {
        return new String(remotePath.getBytes(FtpClientFactory.LOCAL_CHARSET), FtpClientFactory.SERVER_CHARSET);
    }

    private String toLocalCharsetFormat(String remoteString) throws UnsupportedEncodingException {
        return new String(remoteString.getBytes(FtpClientFactory.SERVER_CHARSET), FtpClientFactory.LOCAL_CHARSET);
    }
}
