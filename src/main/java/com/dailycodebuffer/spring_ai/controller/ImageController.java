package com.dailycodebuffer.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ImageController {

    private final ChatModel chatModel;

    @GetMapping("image-to-text")
    public String describeImage() {
        String response = ChatClient.create(chatModel).prompt()
                .user(userSpec -> userSpec.text("Please explain the image in 50 words")
                        .media(MimeTypeUtils.IMAGE_JPEG,
                                new ClassPathResource("/images/images/horse-8209533_1280.jpg")))
                .call().content();
        return response;
    }
}
