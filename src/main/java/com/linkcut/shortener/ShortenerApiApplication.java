package com.linkcut.shortener;

import com.linkcut.shortener.common.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class ShortenerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenerApiApplication.class, args);
    }

}
