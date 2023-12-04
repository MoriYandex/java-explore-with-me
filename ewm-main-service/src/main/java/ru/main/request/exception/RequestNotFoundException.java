package ru.main.request.exception;

import ru.main.exception.NotFoundException;

public class RequestNotFoundException extends NotFoundException {
    public RequestNotFoundException(Long participationId) {
        super(String.format("Request with id=%d was not found", participationId));
    }
}
