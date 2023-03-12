package com.idev.text2pic.model;

import lombok.Data;

@Data
public class Message {
    private int message_id;
    private User from;
    private Chat chat;
    private long date;
    private String text;

    // getters and setters omitted for brevity
}
