package com.devops.helloworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.devops.helloworld.constants.Topics.TOPIC_HELLO_WORLD;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public String publishMessage(String message) {
        for (int i = 0;i<500;i++) {
            String indexedMessage = message + " " + i;
            kafkaTemplate.send(TOPIC_HELLO_WORLD, indexedMessage);
        }
        return "Messages Sent";
    }

    public String publishMessageInOrder(String message) {
        for (int i = 0;i<10;i++) {
            String indexedMessage = message + " " + i;
            kafkaTemplate.send(TOPIC_HELLO_WORLD,"index1", indexedMessage);
        }
        for (int i = 10;i<20;i++) {
            String indexedMessage = message + " " + i;
            kafkaTemplate.send(TOPIC_HELLO_WORLD,"index2", indexedMessage);
        }
        for (int i = 20;i<30;i++) {
            String indexedMessage = message + " " + i;
            kafkaTemplate.send(TOPIC_HELLO_WORLD,"index3", indexedMessage);
        }
        return "Messages Sent";
    }
}
