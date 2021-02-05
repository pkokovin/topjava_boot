package ru.javaops.topjava.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Role;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.repository.MealRepository;
import ru.javaops.topjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
@Slf4j
public class ParserUtil {
    public static User parseToUser(String userString, UserRepository userRepository) {
        String[] fields = userString.split(";");
        String name = fields[0];
        log.info("Name: " + name);
        String email = fields[1];
        log.info("Email: " + email);
        String password = fields[2];
        log.info("Password: " + password);
        int calories = Integer.parseInt(fields[3]);
        log.info("Calories: " + calories);
        Set<Role> roles = new HashSet<>();
        for (int i = 4; i < fields.length; i++) {
            Role role = "USER".equals(fields[i]) ? Role.USER : Role.ADMIN;
            roles.add(role);
        }
        for (Role role : roles) {
            log.info("Role: " + role.toString());
        }
        log.info("Saving user...");
        User user = userRepository.save(
                new User(null, name, email, password, calories, true, new Date(), roles)
        );
        log.info("User saved: " + user.toString());
        return user;
    }

    @SneakyThrows
    public static Meal parseToMeal(String mealString, UserRepository userRepository, MealRepository mealRepository) {
        String[] fields = mealString.split(";");
        LocalDateTime dateTime = LocalDateTime.parse(
                fields[0].replace(" ", fields[0].length() == 18 ? "T0" : "T")
        );
        log.info("Meal dateTime: " + dateTime);
        String description = fields[1];
        log.info("Meal description: " + description);
        int calories = Integer.parseInt(fields[2]);
        log.info("Meal calories: " + calories);
        int userId = Integer.parseInt(fields[3]);
        log.info("UserId: " + userId);
        User user = userRepository.getOne(userId);
        Meal meal = new Meal(null, dateTime, description, calories);
        meal.setUser(user);
        log.info("Saving meal: " + meal);
        Meal savedMeal = mealRepository.save(meal);
        log.info("Meal saved: " + savedMeal);
        return savedMeal;
    }
}
