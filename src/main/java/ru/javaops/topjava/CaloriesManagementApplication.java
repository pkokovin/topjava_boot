package ru.javaops.topjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.javaops.topjava.repository.MealRepository;
import ru.javaops.topjava.repository.UserRepository;
import ru.javaops.topjava.util.CsvUtil;

@SpringBootApplication
@Slf4j
public class CaloriesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaloriesManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(MealRepository mealRepository, UserRepository userRepository, CsvUtil csvUtil) {
        log.info("Filling DB");
        return (args) -> {
            log.info("Parsing...");
            csvUtil.parse(mealRepository, userRepository);
            log.info("Parsing finished!");
        };
    }
}
