package ru.javaops.topjava.util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.javaops.topjava.repository.MealRepository;
import ru.javaops.topjava.repository.UserRepository;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static ru.javaops.topjava.util.ParserUtil.parseToMeal;
import static ru.javaops.topjava.util.ParserUtil.parseToUser;

@Component
@Slf4j
public class CsvUtil {
    @Value("${sys.scan.path}")
    private String path;

    @SneakyThrows
    public void parse(MealRepository mealRepository, UserRepository userRepository) {
        File parserDir = new File(path);
        File[] files = parserDir.listFiles();
        List<String> userStrings = new ArrayList<>();
        List<String> mealStrings = new ArrayList<>();
        assert files != null;
        for (File current : files) {
            if (current.isFile() && current.getName().contains("user") && current.getName().endsWith(".csv")) {
                userStrings.addAll(Files.readAllLines(current.toPath()));
            }
            if (current.isFile() && current.getName().contains("meal") && current.getName().endsWith(".csv")) {
                mealStrings.addAll(Files.readAllLines(current.toPath()));
            }
        }
        for (String userString : userStrings) {
            log.info("Parsing user string: " + userString);
            parseToUser(userString, userRepository);
        }
        for (String mealString : mealStrings) {
            log.info("Parsing meal string: " + mealString);
            parseToMeal(mealString, userRepository, mealRepository);
        }
    }
}
