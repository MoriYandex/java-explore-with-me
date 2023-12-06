package ru.practicum.main.category.exception;

import ru.practicum.main.exception.ConflictException;

public class CategoryConflictException extends ConflictException {
    public CategoryConflictException(Long id) {
        super(String.format("The category %d is not empty", id));
    }
}
