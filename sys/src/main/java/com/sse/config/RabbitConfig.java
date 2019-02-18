package com.sse.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-15 15:49
 */

@Configuration
public class RabbitConfig {

    /*
    对列名
     */
    public static final String DIRECT_QUEUE_NAME = "userAdd";

    @Bean
    public Queue userQueue() {
        return new Queue(DIRECT_QUEUE_NAME);
    }


}
