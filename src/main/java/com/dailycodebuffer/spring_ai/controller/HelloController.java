package com.dailycodebuffer.spring_ai.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;



@RestController
public class HelloController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/celeb-details.st")
    private Resource celebPrompt;

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

    @GetMapping("/celeb")
    public String getCelebDetails(@RequestParam String name) {
        PromptTemplate template = new PromptTemplate(celebPrompt);
        Prompt prompt = template.create(Map.of("name", name));
        return chatClient.prompt(prompt).call().content();
    }
    
}
