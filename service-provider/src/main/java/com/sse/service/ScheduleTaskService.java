package com.sse.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pczhao
 * @email
 * @date 2018-12-13 13:38
 */

@EnableScheduling
@Component
public class ScheduleTaskService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Scheduled(cron = "0 0/1 * * * ?")
    public void writeTime() {
        System.out.println(sdf.format(new Date()));
    }
}
