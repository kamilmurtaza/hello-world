package com.devops.helloworld.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic helloWorldTopic() {
        short replicationFactor = 1;
        return new NewTopic("hello-world", 3, replicationFactor);
    }
}
