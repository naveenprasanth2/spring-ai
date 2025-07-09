package com.dailycodebuffer.spring_ai.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AudioController {

    private final OpenAiAudioTranscriptionModel audioModel;

    @GetMapping("/audio-to-text")
    public String audioTranscription() {
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .temperature(0.5f)
                .language("en")
                .responseFormat(TranscriptResponseFormat.TEXT)
                .build();
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(new ClassPathResource("/audio/sample_audio.mp3"),
                options);
        return audioModel.call(prompt).getResult().getOutput();
    }
}
