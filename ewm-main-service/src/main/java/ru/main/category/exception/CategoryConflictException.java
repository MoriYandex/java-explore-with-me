package ru.main.category.exception;

import ru.main.exception.ConflictException;

public class CategoryConflictException extends ConflictException {
    public CategoryConflictException() {
        super("The category is not empty");
    }
}
