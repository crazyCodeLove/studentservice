package com.sse.util.ftp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 20:38
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FtpPoolConfig extends GenericObjectPoolConfig {
    private String host;//主机名
    private int port = 21;//端口
    private String username;//用户名
    private String password;//密码

    private int connectTimeOut = 5000;//ftp 连接超时时间 毫秒
    private int bufferSize = 1024;//缓冲区大小
    private int fileType = 2;//  传输数据格式   2表binary二进制数据
    private int dataTimeout = 120000;
    private boolean useEPSVwithIPv4 = false;
    private boolean passiveMode = true;//是否启用被动模式
}
