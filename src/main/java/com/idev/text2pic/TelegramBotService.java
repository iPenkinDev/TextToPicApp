package com.idev.text2pic;

import com.google.gson.Gson;
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
    private final TelegramBotConfig telegramBotConfig;

    private Gson gson = new Gson();

    private final WebClient webClient;

    @Autowired
    public TelegramBotService(TelegramBotConfig telegramBotConfig, WebClient webClient) {
        this.telegramBotConfig = telegramBotConfig;
        this.webClient = webClient;
    }

    public TelegramResponse getUpdates(long offset) {
        System.out.println("Getting updates");
        String url = telegramBotConfig.getTelegramApiUrl() + telegramBotConfig.getBotToken()
                + "/getUpdates?offset=" + offset + "&timeout=20";
        System.out.println("url=" + url);
        String json = webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        TelegramResponse response = gson.fromJson(json, TelegramResponse.class);
        return response;
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
