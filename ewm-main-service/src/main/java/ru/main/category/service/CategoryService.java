package ru.main.category.service;

import org.springframework.data.domain.PageRequest;
import ru.main.category.dto.CategoryDto;
import ru.main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(PageRequest pageRequest);

    CategoryDto getCategoryById(Long categoryId);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long categoryId);

    CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto);
}
