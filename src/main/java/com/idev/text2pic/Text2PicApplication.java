package com.idev.text2pic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Text2PicApplication {

	private BotController controller;

	@Autowired
	public Text2PicApplication(BotController controller) {
		this.controller = controller;

		System.out.println("Text2PicApplication");
	}

	public static void main(String[] args) {
		SpringApplication.run(Text2PicApplication.class, args);
	}

}
