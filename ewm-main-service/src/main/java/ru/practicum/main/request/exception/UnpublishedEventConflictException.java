package ru.practicum.main.request.exception;

import ru.practicum.main.exception.ConflictException;

public class UnpublishedEventConflictException extends ConflictException {
    public UnpublishedEventConflictException() {
        super("You can't participate in an unpublished event");
    }
}
