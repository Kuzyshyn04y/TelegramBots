package io.project.springbot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    String botName;

    @Value("${bot.key}")
    String botToken;

    public BotConfig(){ }

    public String getToken(){
        return botToken;
    }

    public String getBotName(){
        return botName;
    }
}
