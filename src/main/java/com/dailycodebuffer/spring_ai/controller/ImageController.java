package com.dailycodebuffer.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ImageController {

    private final ChatModel chatModel;
    private final ImageModel imageModel;

    @GetMapping("image-to-text")
    public String describeImage() {
        String response = ChatClient.create(chatModel).prompt()
                .user(userSpec -> userSpec.text("Please decribe the image in 10 words")
                        .media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("/images/horse-8209533_1280.jpg")))
                .call()
                .content();
        return response;
    }

    @GetMapping("/image/{prompt}")
    public String generateImage(@PathVariable String prompt) {
        ImageResponse response = imageModel.call(new ImagePrompt(prompt,
                OpenAiImageOptions.builder().N(1)
                        .quality("hd")
                        .width(1024).height(1024).build()));
        return response.getResult().getOutput().getUrl();
    }

}
