package org.example.weaherbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class InitBot {
    @Value("${nickname}")
    private String name;
    @Value("${key}")
    private String token;
}
