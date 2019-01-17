package com.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author pczhao
 * @email
 * @date 2019-01-17 9:42
 */

@Configuration
@ComponentScan(basePackages = "com.sse")
public class BeanRegister2Context {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
