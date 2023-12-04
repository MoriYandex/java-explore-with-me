package ru.main.request.exception;

import ru.main.exception.ConflictException;

public class RequestUserConflictException extends ConflictException {
    public RequestUserConflictException() {
        super("You can't cancel another user's request");
    }
}
