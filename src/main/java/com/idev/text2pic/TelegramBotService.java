package com.idev.text2pic;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Log4j
public class TelegramBotService {
    private final TelegramBotConfig telegramBotConfig = new TelegramBotConfig();

    private final WebClient webClient;

    @Autowired
    public TelegramBotService(WebClient webClient) {
        System.out.println("TelegramBotService initialized");
        this.webClient = webClient;
    }

    public String getUpdates() {
        System.out.println("Getting updates");
        String url = telegramBotConfig.getTelegramApiUrl() + telegramBotConfig.getBotToken() + "/getUpdates";
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Mono<String> sendMessage(String message) {
        log.debug("Sending message: " + message);
        String url = telegramBotConfig.getTelegramApiUrl() + telegramBotConfig.getBotToken() + "/sendMessage?chat_id=" + telegramBotConfig.getBotName() + "&text=" + message;
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
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
