package com.dailycodebuffer.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    

    private final ChatClient chatClient;
    

    public HelloController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @PostMapping("/message")
    public String prompt(@RequestBody String message){
       return chatClient.prompt(message).call().content();
    }
}
