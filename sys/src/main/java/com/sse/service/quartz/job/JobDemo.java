package com.sse.service.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-09 11:19
 */

@Slf4j
public class JobDemo implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        System.out.println("now time: {}" + sdf.format(new Date()));
        System.out.println("fire time:" + sdf.format(jobExecutionContext.getFireTime()));
        System.out.println("next fire time:" + sdf.format(jobExecutionContext.getNextFireTime()));

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        Trigger trigger = jobExecutionContext.getTrigger();

        Object jobMessage = jobDetail.getJobDataMap().get("message");
        System.out.println("jobMessage:" + jobMessage.toString());
        Object triggerMessage = trigger.getJobDataMap().get("message");
        System.out.println("triggerMessage:" + triggerMessage.toString());

        JobKey jobKey = jobDetail.getKey();
        System.out.println("jobkey; name: " + jobKey.getName() + ", group: " + jobKey.getGroup());
        TriggerKey triggerKey = trigger.getKey();
        System.out.println("triggerKey; name: " + triggerKey.getName() + ", group: " + triggerKey.getGroup());

    }
}
