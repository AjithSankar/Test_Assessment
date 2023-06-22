package com.example.cucumberrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CucumberRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CucumberRestApplication.class, args);
    }

}
