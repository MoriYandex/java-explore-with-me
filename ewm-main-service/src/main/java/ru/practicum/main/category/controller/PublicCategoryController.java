package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get(@RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                 @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return categoryService.get(PageRequest.of(from, size));
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getById(@PathVariable @Positive Long categoryId) {
        return categoryService.getById(categoryId);
    }
}
