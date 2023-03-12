package com.idev.text2pic.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private boolean is_bot;
    private String first_name;
    private String username;
    private String language_code;

    // getters and setters omitted for brevity
}
