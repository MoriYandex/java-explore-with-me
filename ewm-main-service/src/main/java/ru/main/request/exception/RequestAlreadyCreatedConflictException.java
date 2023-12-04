package ru.main.request.exception;

import ru.main.exception.ConflictException;

public class RequestAlreadyCreatedConflictException extends ConflictException {
    public RequestAlreadyCreatedConflictException() {
        super("Event request already created");
    }
}
