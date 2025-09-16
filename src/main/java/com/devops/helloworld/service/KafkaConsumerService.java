package com.devops.helloworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import static com.devops.helloworld.constants.Topics.TOPIC_HELLO_WORLD;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ConsumerFactory<String, String> kafkaConsumerServiceFactory;

    @KafkaListener(groupId = "hello-world",
            topics = TOPIC_HELLO_WORLD,
            containerFactory = "kafkaConsumerServiceContainerFactory")
    public void consumeMessages(String message, Acknowledgment acknowledgment) {
        // container factory defined for manual configs, here for manual ack, check config class
        System.out.println("Message Received: " + message);
        acknowledgment.acknowledge();
    }
}
