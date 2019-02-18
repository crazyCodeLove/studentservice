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

    public static final String ROUTE_KEY = "USER_ADD";

    @Bean
    public Queue userQueue() {
        return new Queue(ROUTE_KEY);
    }


}
