package com.sse.util.ftp;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 20:45
 */

@Setter
@Getter
public class FtpClientPool {
    /**
     * ftp客户端连接池
     */
    private GenericObjectPool<FTPClient> pool;

    /**
     * ftp客户端工厂
     */
    private FtpClientFactory clientFactory;


    /**
     * 构造函数中 注入一个bean
     */
    public FtpClientPool(FtpClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        pool = new GenericObjectPool<>(clientFactory, clientFactory.getFtpPoolConfig());
    }


    /**
     * 获取一个连接对象
     */
    public FTPClient getFtpClient() throws Exception {
        return pool.borrowObject();
    }

    /**
     * 归还一个连接对象
     */
    public void returnClient(FTPClient ftpClient) {
        if (ftpClient != null) {
            pool.returnObject(ftpClient);
        }
    }
}
