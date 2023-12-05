package ru.practicum.main.request.exception;

import ru.practicum.main.exception.ConflictException;

public class RequestUserConflictException extends ConflictException {
    public RequestUserConflictException() {
        super("You can't cancel another user's request");
    }
}
