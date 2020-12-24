package ru.javaops.topjava;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class CaloriesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaloriesManagementApplication.class, args);
    }
}
