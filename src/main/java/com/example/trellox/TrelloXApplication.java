package com.example.trellox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TrelloXApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloXApplication.class, args);
    }

}
