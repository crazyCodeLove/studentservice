package com.sse.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-13 20:47
 */

@EnableScheduling
@Component
public class ScheduleService extends ServiceBase {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 秒 分 时 天 月 星期 ，以下表示每天每隔2小时执行一次
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void writeTime() {
        System.out.println(sdf.format(new Date()));
    }

}
