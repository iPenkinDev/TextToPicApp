package com.dev.texttopicapp.controller;

import com.dev.texttopicapp.config.TelegramBotConfig;
import com.dev.texttopicapp.service.TelegramBotService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.text.MessageFormat;

@RestController
@Log4j
public class TelegramBotController {

    private final TelegramBotService telegramBotService;

    @Autowired
    public TelegramBotController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @GetMapping("/sendMessage")
    public Mono<String> sendMessage(@RequestParam String message) {
        log.debug(MessageFormat.format("Sending message: ", message));
        return telegramBotService.sendMessage(message);
    }
}