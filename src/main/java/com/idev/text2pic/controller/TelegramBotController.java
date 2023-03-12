package com.idev.text2pic.controller;

import com.idev.text2pic.model.Result;
import com.idev.text2pic.model.TelegramResponse;
import com.idev.text2pic.service.TelegramBotService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotController {

    private TelegramBotService telegramBotService;
    private long offset;

    public TelegramBotController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
        System.out.println("TelegramBotController");
    }

    @PostConstruct
    public void startUpdatesLoop() {
        System.out.println("Start updates loop");
        updatesLoop();
    }

    private void updatesLoop() {
        System.out.println("do request");
        TelegramResponse response = telegramBotService.getUpdates(offset);
        if (response.isOk()) {
            for (Result result : response.getResult()) {
                offset = result.getUpdate_id() + 1 ;
                System.out.println("new offset=" + offset);
                String messageText = result.getMessage().getText();
                System.out.println("message=" + messageText);

                int chatId = result.getMessage().getChat().getId();
                String message = createAnswerWithChatGpt(messageText);

                String sendMsgResponse = telegramBotService.sendMessage(message, chatId);
                System.out.println("response for send message=" + sendMsgResponse);
            }
        }else {
            System.out.println("error of updates request TODO - parse error code + description =" + "");
        }
        System.out.println("response=" + response);
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        updatesLoop();
    }

    private static String createAnswerWithChatGpt(String messageText) {
        return "bot answer for message=" + messageText;
    }
}
