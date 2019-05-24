package com.sse.util.ftp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 20:40
 */

@Slf4j
@Getter
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {

    // FTP协议里面，规定文件名编码为iso-8859-1
    public static final String SERVER_CHARSET = "ISO-8859-1";
    public static String LOCAL_CHARSET = "GBK";
    private FtpPoolConfig ftpPoolConfig;

    public FtpClientFactory(FtpPoolConfig ftpPoolConfig) {
        this.ftpPoolConfig = ftpPoolConfig;
    }

    /**
     * 新建对象
     */
    @Override
    public FTPClient create() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(ftpPoolConfig.getConnectTimeOut());
        try {
            log.info("connect to ftp server:" + ftpPoolConfig.getHost() + ":" + ftpPoolConfig.getPort());
            ftpClient.connect(ftpPoolConfig.getHost(), ftpPoolConfig.getPort());
            // 检验登陆操作的返回码是否正确
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                String errorMsg = "FTPServer reject connection";
                log.error(errorMsg);
                throw new Exception(errorMsg);
            }
            boolean result = ftpClient.login(ftpPoolConfig.getUsername(), ftpPoolConfig.getPassword());
            if (!result) {
                log.error("ftpClient login failed!");
                throw new Exception("ftpClient login failed! userName:" + ftpPoolConfig.getUsername()
                        + ", password:" + ftpPoolConfig.getPassword());
            }
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                LOCAL_CHARSET = "UTF-8";
            }
            log.info("local charset is " + LOCAL_CHARSET);
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            ftpClient.setBufferSize(ftpPoolConfig.getBufferSize());
            ftpClient.setFileType(ftpPoolConfig.getFileType());
            ftpClient.setDataTimeout(ftpPoolConfig.getDataTimeout());
            ftpClient.setUseEPSVwithIPv4(ftpPoolConfig.isUseEPSVwithIPv4());
            if (ftpPoolConfig.isPassiveMode()) {
                log.info("into ftp passive mode");
                ftpClient.enterLocalPassiveMode();//进入被动模式
            }
        } catch (IOException e) {
            log.error("FTP connect failed：", e);
            throw new Exception("FTP connect failed, " + e.getMessage());
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.logout();
        super.destroyObject(p);
    }

    /**
     * 验证对象
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        boolean connect = false;
        try {
            connect = ftpClient.sendNoOp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
