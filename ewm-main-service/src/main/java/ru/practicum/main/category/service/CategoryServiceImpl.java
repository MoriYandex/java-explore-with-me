package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.exception.CategoryConflictException;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.util.SharedRequests.checkAndReturnCategory;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    public List<CategoryDto> get(PageRequest pageRequest) {
        List<Category> categories = categoryRepository.findAll(pageRequest).getContent();
        log.info("Get categories. PageRequest: {}, categories: {}", pageRequest, categories);
        return categories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        log.info("Get category by id. CategoryId: {}", categoryId);
        return CategoryMapper.toCategoryDto(checkAndReturnCategory(categoryRepository, categoryId));
    }

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        log.info("Create category by id. NewCategoryDto: {}", newCategoryDto);
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        log.info("Delete category by id. CategoryId: {}", categoryId);
        Category category = checkAndReturnCategory(categoryRepository, categoryId);
        if (eventRepository.countByCategoryId(categoryId) > 0L) {
            throw new CategoryConflictException(categoryId);
        }
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto) {
        log.info("Update category. CategoryId: {}, NewCategoryDto: {}", categoryId, newCategoryDto);
        Category category = checkAndReturnCategory(categoryRepository, categoryId);
        category.setName(newCategoryDto.getName());
        return CategoryMapper.toCategoryDto(category);
    }
}
