package ru.practicum.main.category.exception;

import ru.practicum.main.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id = %d was not found", id));
    }
}
