package com.sse.adapter.mybatis.mapper;

import com.sse.model.log.LogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * author pczhao <br/>
 * date 2019-01-18 15:53
 */

@Mapper
public interface LogMapper {
    /**
     * 保存请求日志信息到数据库
     *
     * @param logInfo 请求信息
     */
    void save(LogInfo logInfo);

}
