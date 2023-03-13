package com.idev.text2pic.service;

import com.google.gson.Gson;
import com.idev.text2pic.config.TelegramBotConfig;
import com.idev.text2pic.model.TelegramResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.HashMap;

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

    public String sendMessage(String message, int chatId) {
        log.debug("Sending message: " + message);
        String url = telegramBotConfig.getTelegramApiUrl() + telegramBotConfig.getBotToken()
                + "/sendMessage";
        HashMap<String, Object> map = new HashMap<>();
        map.put("chat_id", chatId);
        map.put("text", message);

        // send webclient request with post method and body

        return webClient
                .post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("content-type", "application/json")
                .bodyValue(gson.toJson(map))
                .retrieve()
                .onStatus(code -> code.value() >= 400,
                        response -> response.bodyToMono(String.class).flatMap(body -> {
                            throw new RuntimeException("Client error: " + body);
                        }))

                .bodyToMono(String.class)
                .block();
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
