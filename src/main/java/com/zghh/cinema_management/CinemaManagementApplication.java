package com.zghh.cinema_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

//@EntityScan("com.zghh.cinema_management.bean")
@EnableJpaRepositories(basePackages = "com.zghh.cinema_management.repository")
public class CinemaManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaManagementApplication.class, args);
    }

}
