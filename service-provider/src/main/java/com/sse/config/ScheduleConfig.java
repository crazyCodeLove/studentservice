package com.sse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 添加调度系统配置。schedule 为每项任务分配一个线程
 *
 * author pczhao
 * date 2018-12-20 14:21
 */

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private int THREAD_POOL_SIZE = 20;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(THREAD_POOL_SIZE));
    }
}
