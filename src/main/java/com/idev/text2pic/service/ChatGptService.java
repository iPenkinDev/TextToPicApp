package com.idev.text2pic.service;

import com.idev.text2pic.config.ChatGptConfig;
import com.idev.text2pic.model.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Log4j
public class ChatGptService {

    private final WebClient webClient;

    private ChatGptConfig chatGptConfig;
    private Message message;

    @Autowired
    public ChatGptService(WebClient webClient, ChatGptConfig chatGptConfig, Message message) {
        this.chatGptConfig = chatGptConfig;
        this.webClient = webClient;
        this.message = message;
    }

    public Mono<String> getResponse(Message message) {
        return webClient.post()
                .uri(chatGptConfig.getChatUrl())
                .header("Authorization", "Bearer " + chatGptConfig.getChatToken())
                .header("Content-Type", "application/json")
                .bodyValue("{ \"model\": \"gpt-3.5-turbo\", " +
                        message + ": [" +
                        "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}," +
                        "{\"role\": \"user\", \"content\": \"What is the OpenAI mission?\"}" +
                        "] }")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Bean
    public static WebClient webClient() {
        HttpClient httpClient = HttpClient.create();
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
