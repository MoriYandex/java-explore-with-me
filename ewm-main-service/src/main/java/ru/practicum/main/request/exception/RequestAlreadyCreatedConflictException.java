package ru.practicum.main.request.exception;

import ru.practicum.main.exception.ConflictException;

public class RequestAlreadyCreatedConflictException extends ConflictException {
    public RequestAlreadyCreatedConflictException() {
        super("Event request already created");
    }
}
