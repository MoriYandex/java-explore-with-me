package ru.practicum.main.category.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.main.category.exception.CategoryNotFoundException;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;

@Slf4j
@UtilityClass
public class SharedCategoryRequests {
    public static Category checkAndReturnCategory(CategoryRepository categoryRepository, Long categoryId) {
        log.info("SharedCategoryRequests check category. Category id: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
