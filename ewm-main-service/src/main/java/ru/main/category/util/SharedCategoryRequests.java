package ru.main.category.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.main.category.exception.CategoryNotFoundException;
import ru.main.category.model.Category;
import ru.main.category.repository.CategoryRepository;

@Slf4j
@UtilityClass
public class SharedCategoryRequests {
    public static Category checkAndReturnCategory(CategoryRepository categoryRepository, Long categoryId) {
        log.info("SharedCategoryRequests check category. Category id: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
