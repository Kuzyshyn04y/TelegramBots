package org.example.weaherbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
public class WeaherBotApplication {

	public static void main(String[] args) {

		SpringApplication.run(WeaherBotApplication.class, args);
	}

}
