package com.fitness.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini_url}")
    private String gemini_url;

    @Value("${gemini_key}")
    private String gemini_key;

    private final WebClient webClient;

    public String getAnswer(String question){
        System.out.println("Gemini URL & Key: " + gemini_url + gemini_key);
        System.out.println("payload: " + payload(question));
        return webClient.post()
                .uri(gemini_url+gemini_key)
                .bodyValue(payload(question))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String,Object> payload(String question) {
        return Map.of("contents", new Object[] {
                Map.of("parts", new Object[] {
                        Map.of("text", question)
                })
        });
    }
}
