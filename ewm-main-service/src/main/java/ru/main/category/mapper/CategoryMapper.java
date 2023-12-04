package ru.main.category.mapper;

import lombok.experimental.UtilityClass;
import ru.main.category.dto.CategoryDto;
import ru.main.category.dto.NewCategoryDto;
import ru.main.category.model.Category;

@UtilityClass
public class CategoryMapper {
    public static Category toEntity(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
