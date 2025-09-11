package com.devops.helloworld.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloController {

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
}
