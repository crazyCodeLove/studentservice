package com.sse.service;

import com.sse.adapter.mybatis.mapper.LogMapper;
import com.sse.model.log.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pczhao
 * @email
 * @date 2019-01-18 15:29
 */

@Service
public class LogService {

    private LogMapper logMapper;

    @Autowired
    public LogService(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Async
    @Transactional
    public void save(LogInfo logInfo) {
        logMapper.save(logInfo);
    }
}
