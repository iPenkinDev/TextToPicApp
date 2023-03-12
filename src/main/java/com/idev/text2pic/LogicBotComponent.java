package com.idev.text2pic;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class LogicBotComponent {

    private TelegramBotService telegramBotService;
    private long offset;

    public LogicBotComponent(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
        System.out.println("LogicBotComponent");
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
                System.out.println("message=" + result.getMessage().getText());
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
}
