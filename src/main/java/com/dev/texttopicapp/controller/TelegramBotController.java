package com.dev.texttopicapp.controller;

import com.dev.texttopicapp.service.TelegramBotService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Controller
@Log4j
public class TelegramBotController {
    private final TelegramBotService telegramBotService;

    @Autowired
    public TelegramBotController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @PostMapping("/sendMessage")
    public Mono<String> sendMessage(@RequestParam String message) {
        log.debug(MessageFormat.format("Sending message: ", message));
        return telegramBotService.sendMessage(message);
    }
    @PostMapping("/getUpdates")
    public String getUpdates() {
        log.debug(telegramBotService.getUpdates());
        return telegramBotService.getUpdates();
    }



}