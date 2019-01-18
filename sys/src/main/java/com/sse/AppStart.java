package com.sse;

import com.sse.config.ScheduleConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * author pczhao
 * date 2018-11-05 21:29
 */

@Import(ScheduleConfig.class)
@MapperScan(basePackages = "com.sse")
@EnableAsync
@SpringBootApplication
@EnableEurekaClient
public class AppStart {

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}
