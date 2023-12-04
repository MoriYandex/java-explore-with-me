package ru.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.main.category.dto.CategoryDto;
import ru.main.category.dto.NewCategoryDto;
import ru.main.category.mapper.CategoryMapper;
import ru.main.category.model.Category;
import ru.main.category.util.SharedCategoryRequests;
import ru.main.event.repository.EventRepository;
import ru.main.category.exception.CategoryConflictException;
import ru.main.category.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    public List<CategoryDto> getCategories(PageRequest pageRequest) {
        List<Category> categories = categoryRepository.findAll(pageRequest).getContent();
        log.info("Category Service. Get categories. PageRequest: {}, categories: {}", pageRequest, categories);
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        return CategoryMapper.toDto(SharedCategoryRequests.checkAndReturnCategory(categoryRepository, categoryId));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        log.info("Category Service. Create category by id. NewCategoryDto: {}", newCategoryDto);
        Category category = categoryRepository.save(CategoryMapper.toEntity(newCategoryDto));
        return CategoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        log.info("Category Service. Delete category by id. CategoryId: {}", categoryId);
        Category category = SharedCategoryRequests.checkAndReturnCategory(categoryRepository, categoryId);
        if (eventRepository.countByCategoryId(categoryId) > 0L) {
            throw new CategoryConflictException();
        }
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto) {
        log.info("Category Service. Update category. CategoryId: {}, NewCategoryDto: {}", categoryId, newCategoryDto);
        Category category = SharedCategoryRequests.checkAndReturnCategory(categoryRepository, categoryId);
        category.setName(newCategoryDto.getName());
        return CategoryMapper.toDto(category);
    }
}
