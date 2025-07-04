package com.dailycodebuffer.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final ChatClient chatClient;

    public HelloController(@Autowired ChatClient.Builder builder){
        chatClient = builder.build();
    }

    @GetMapping
    public String prompt(@RequestParam String message) {
        return chatClient.prompt(message)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
    }
}
