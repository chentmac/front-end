package com.springcloud.task.controller;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RetryerConfig {
    @Bean
    @Primary
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }
}
