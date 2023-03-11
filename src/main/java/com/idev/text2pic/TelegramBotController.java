package com.idev.text2pic;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Controller
@Log4j
@Component
public class TelegramBotController {
    private final TelegramBotService telegramBotService;

    @Autowired
    public TelegramBotController(TelegramBotService telegramBotService) {
        System.out.println("TelegramBotController");
        this.telegramBotService = telegramBotService;

    }

    @PostMapping("/sendMessage")
    public Mono<String> sendMessage(@RequestParam String message) {
        log.debug(MessageFormat.format("Sending message: ", message));
        return telegramBotService.sendMessage(message);
    }
    @PostMapping("/getUpdates")
    public String getUpdates() {
        String updates = telegramBotService.getUpdates();
        log.debug(MessageFormat.format("Getting updates: ", updates));
        return updates;
    }
}