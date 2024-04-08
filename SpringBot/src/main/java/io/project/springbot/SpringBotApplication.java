package io.project.springbot;

import io.project.springbot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBotApplication {

    public static void main(String[] args) {
        String[] a = new String[1];

        SpringApplication.run(SpringBotApplication.class, args);
    }

}
