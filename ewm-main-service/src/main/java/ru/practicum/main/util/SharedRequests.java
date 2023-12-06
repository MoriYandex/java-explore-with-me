package ru.practicum.main.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

@Slf4j
@UtilityClass
public class SharedRequests {
    public static Category checkAndReturnCategory(CategoryRepository categoryRepository, Long categoryId) {
        log.info("Check category. Category id: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    public static User checkAndReturnUser(UserRepository userRepository, Long userId) {
        log.info("Check user. User id: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
