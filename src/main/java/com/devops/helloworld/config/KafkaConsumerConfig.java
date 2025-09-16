package com.devops.helloworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaConsumerConfig {

    @Bean(name = "kafkaConsumerServiceContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaConsumerServiceContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); // can also be done via application property using spring.kafka.listener.ack-mode=manual_immediate

        factory.setConcurrency(3); // similar to spring.kafka.listener.concurrency=3

        return factory;
    }
}