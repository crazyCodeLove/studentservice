package com.sse.service.quartz;

import com.sse.service.quartz.job.JobDemo;
import org.junit.Test;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-09 11:21
 */

public class QuartzManagerTest {
    @Test
    public void quartz() {
        try {
            String jobName = "动态任务调度demo";
            String jobGroupName = "动态任务调度组demo";
            String triggerName = "触发器demo";
            String triggerGroupName = "触发器组demo";
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            QuartzManager.addCronJob(jobName, jobGroupName, JobDemo.class, triggerName, triggerGroupName, "0/1 * * * * ?", null);

            Thread.sleep(5000);
            System.out.println("【修改时间】开始(每2秒输出一次)...");
            QuartzManager.modifyCronJobTime(triggerName, triggerGroupName, "0/2 * * * * ?");
            Thread.sleep(10000);
            System.out.println("【移除定时】开始...");
            QuartzManager.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            System.out.println("【移除定时】成功");
            Thread.sleep(50);
            System.out.println("【关闭任务调度】开始...");
            QuartzManager.shutdownAllJobs();
            System.out.println("【关闭任务调度】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
