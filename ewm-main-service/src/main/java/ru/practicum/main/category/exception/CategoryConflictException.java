package ru.practicum.main.category.exception;

import ru.practicum.main.exception.ConflictException;

public class CategoryConflictException extends ConflictException {
    public CategoryConflictException() {
        super("The category is not empty");
    }
}
