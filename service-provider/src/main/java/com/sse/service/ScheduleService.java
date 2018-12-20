package com.sse.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Scheduled(cron = "0/1 * * * * ?")
    public void writeTime() {
        System.out.println("thread ID:" + Thread.currentThread().getId() + ", time:" + sdf.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread ID:" + Thread.currentThread().getId() + " done");
    }

}
