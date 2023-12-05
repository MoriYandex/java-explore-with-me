package ru.practicum.main.category.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> get(PageRequest pageRequest);

    CategoryDto getById(Long categoryId);

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(Long categoryId);

    CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto);
}
