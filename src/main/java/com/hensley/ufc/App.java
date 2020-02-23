package com.hensley.ufc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ImportResource("classpath:applicationContext.xml")
@EnableJpaRepositories(basePackages = "com.hensley.ufc.repository")
@EntityScan("com.hensley.ufc.domain")

public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
};
