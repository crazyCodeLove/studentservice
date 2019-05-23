package com.sse.util.ftp;

import com.sse.util.IOUtil;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 20:51
 */

@Setter
public class FtpClientHelper {
    private FtpClientPool ftpClientPool;

    /**
     * 下载 remote文件流
     *
     * @param remote 远程文件
     * @return 字节数据
     */
    public byte[] downloadFile(String remote) throws Exception {
        FTPClient client = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            client = ftpClientPool.getFtpClient();
            inputStream = client.retrieveFileStream(remote);
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
    public boolean uploadFile(String remote, InputStream local) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.storeFile(remote, local);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 创建目录    单个不可递归
     *
     * @param pathname 目录名称
     */
    public boolean makeDirectory(String pathname) throws Exception {
        FTPClient client = null;
        try {
            client = ftpClientPool.getFtpClient();
            return client.makeDirectory(pathname);
        } finally {
            ftpClientPool.returnClient(client);
        }
    }

    /**
     * 删除目录，单个不可递归
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
