package ru.practicum.main.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
