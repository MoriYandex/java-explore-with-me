package ru.practicum.main.compilation.exception;

import ru.practicum.main.exception.NotFoundException;

public class CompilationNotFoundException extends NotFoundException {
    public CompilationNotFoundException(Long compilationId) {
        super(String.format("Compilation with id=%d was not found", compilationId));
    }
}
