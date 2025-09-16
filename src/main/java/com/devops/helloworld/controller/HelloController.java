package com.devops.helloworld.controller;

import com.devops.helloworld.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class HelloController {

    private final KafkaProducerService kafkaProducerService;

    @Value("${greeting.customer.name}")
    private String name;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World! 🚀";
    }

    @GetMapping("/greetings")
    public String sayCustomHello() {
        return String.join("", "Presenting you Configurable {", name, "} \uD83D\uDE02");
    }

    @GetMapping("/message")
    public String produceMessage(@RequestParam("message") String message) {
        return kafkaProducerService.publishMessageInOrder(message);
    }
}
