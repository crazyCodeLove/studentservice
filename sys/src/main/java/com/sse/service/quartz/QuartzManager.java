package com.sse.service.quartz;

import com.sse.service.quartz.job.JobDemo;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-08 16:57
 */

public class QuartzManager {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名  （带参数）
     *
     * @param jobName  任务名
     * @param jobCls   任务
     * @param cronTime 时间设置，参考quartz说明文档
     */
    public static void addCronJob(String jobName, String jobGroupName, Class<? extends Job> jobCls,
                                  String triggerName, String triggerGroupName, String cronTime,
                                  Map<String, Object> parameter) {
        try {
            //通过 SchedulerFactory 构建 Scheduler 对象
            Scheduler scheduler = schedulerFactory.getScheduler();
            //用于描叙 Job 实现类及其他的一些静态信息，构建一个作业实例
            JobDetail jobDetail = JobBuilder.newJob(jobCls).withIdentity(jobName, jobGroupName).build();
            //传参数
            if (parameter != null && !parameter.isEmpty()) {
                jobDetail.getJobDataMap().put("parameterList", parameter);
            }
            //创建一个新的TriggerBuilder来规范一个触发器
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    //给触发器起一个名字和组名
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();        // 启动
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      任务名称
     * @param triggerGroupName 传过来的任务名称
     * @param cronTime         更新后的时间规则
     */
    public static void modifyCronJobTime(String triggerName, String triggerGroupName, String cronTime) {
        try {
            //通过 SchedulerFactory 构建 Scheduler 对象
            Scheduler scheduler = schedulerFactory.getScheduler();
            //通过触发器名和组名获取 TriggerKey
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //通过 TriggerKey 获取 CronTrigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cronTime)) {
                //重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))
                        .build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            //通过触发器名和组名获取TriggerKey
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //通过任务名和组名获取JobKey
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startAllJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdownAllJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(JobDemo.class).withIdentity("job1", "jobgroup1")
                .usingJobData("message", "job message")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .usingJobData("message","triggerMessage")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(3).withIntervalInSeconds(3))
                .startNow().build();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }

}
