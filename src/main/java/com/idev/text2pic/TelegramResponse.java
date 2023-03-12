package com.idev.text2pic;

import lombok.Data;

import java.util.List;

@Data
public class TelegramResponse {
    private boolean ok;
    private List<Result> result;


}
