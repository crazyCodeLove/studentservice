package com.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @email
 * @date 2018-11-05 21:29
 */

@SpringBootApplication
@EnableEurekaClient
public class StudentServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApp.class, args);
    }
}
