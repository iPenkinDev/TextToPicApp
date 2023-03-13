package com.idev.text2pic.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ChatGptConfig {

    @Value("${chat.gpt.url}")
    private String chatUrl;

    @Value("${chat.gpt.token}")
    private String chatToken;
}
