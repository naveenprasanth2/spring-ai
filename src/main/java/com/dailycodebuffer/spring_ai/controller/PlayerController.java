package com.dailycodebuffer.spring_ai.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.spring_ai.model.Achievement;
import com.dailycodebuffer.spring_ai.model.Player;

@RestController
public class PlayerController {

    private final ChatClient chatClient;

    public PlayerController(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    @GetMapping("/player")
    public List<Player> getPlayerAchievement(@RequestParam String name) {
        BeanOutputConverter<List<Player>> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {

        });
        String message = """
                Generate a list of Career achievements for the sportsperson {sports}.
                Include the Player as the key and achievements as the value for it
                {format}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("sports", name, "format", converter.getFormat()));
        // ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();

        // return chatResponse.getResult().getOutput();
        @SuppressWarnings("null")
        Generation response = chatClient.prompt(prompt).call().chatResponse().getResult();
        return converter.convert(Optional.ofNullable(response.getOutput().getText()).orElse("[]"));
    }

    @GetMapping("/achievement/player")
    public List<Achievement> getAchievement(@RequestParam String name) {
        BeanOutputConverter<List<String>> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {

        });
        String message = """
                    Provide a list of achievemenrs for {player} {format}
                """;
        PromptTemplate template = new PromptTemplate(message);
        Prompt prompt = template.create(Map.of("player", name, "format", converter.getFormat()));
        return chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<Achievement>>() {
        });
    }
}
