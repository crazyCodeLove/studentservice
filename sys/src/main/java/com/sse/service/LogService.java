package com.sse.service;

import com.sse.adapter.mybatis.mapper.LogMapper;
import com.sse.exception.ExceptionCodeEnum;
import com.sse.model.log.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * author pczhao
 * date 2019-01-18 15:29
 */

@Service
@Slf4j
public class LogService {
    // 慢响应时间阈值，单位 ms
    private static final long SLOW_RESPONSE_THRESHOLD = 2000L;

    private LogMapper logMapper;

    @Autowired
    public LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    /**
     * 先打印详细信息到 log 中，再输出到数据库中。防止数据库错误，日志信息不能记录下来。
     * 数据库中只记录异常信息、慢响应请求
     *
     * @param logInfo 日志信息
     */
    @Async
    public void save(LogInfo logInfo) {
        log.info(logInfo.toString());
        if (ExceptionCodeEnum.SUCCESS.getCode() != logInfo.getCode() || logInfo.getDuration() > SLOW_RESPONSE_THRESHOLD) {
            logMapper.save(toFixedLogInfo(logInfo));
        }
    }

    /**
     * 字符串长度不超过 1024 长度
     *
     * @param content 内容
     * @return 结果
     */
    private static String toFixedLengthStr(String content) {
        if (content != null && content.length() > 1024) {
            return content.substring(0, 1024);
        }
        return content;
    }

    private LogInfo toFixedLogInfo(LogInfo srcInfo) {
        return LogInfo.builder()
                .url(toFixedLengthStr(srcInfo.getUrl()))
                .method(srcInfo.getMethod())
                .queryString(toFixedLengthStr(srcInfo.getQueryString()))
                .ip(srcInfo.getIp())
                .callClass(toFixedLengthStr(srcInfo.getCallClass()))
                .callMethod(toFixedLengthStr(srcInfo.getCallMethod()))
                .params(toFixedLengthStr(srcInfo.getParams()))
                .result(toFixedLengthStr(srcInfo.getResult()))
                .code(srcInfo.getCode())
                .message(toFixedLengthStr(srcInfo.getMessage()))
                .responseTime(srcInfo.getResponseTime())
                .duration(srcInfo.getDuration())
                .build();
    }
}
