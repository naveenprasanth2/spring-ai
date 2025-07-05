package com.dailycodebuffer.spring_ai.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final ChatClient chatClient;

    public PlayerController(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    @GetMapping("/player")
    public String getPlayerAchievement(@RequestParam String name) {
        String message = """
                Generate a list of Career achievements for the sportsperson {sports}.
                Include the Player as the key and achievements as the value for it
                """;
        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("sports", name));
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();

        return chatResponse.getResult().getOutput().getText();
    }
}
