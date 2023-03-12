package com.idev.text2pic.model;

import com.idev.text2pic.model.Result;
import lombok.Data;

import java.util.List;

@Data
public class TelegramResponse {
    private boolean ok;
    private List<Result> result;


}
