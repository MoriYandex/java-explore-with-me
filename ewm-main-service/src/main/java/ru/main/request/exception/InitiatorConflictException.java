package ru.main.request.exception;

import ru.main.exception.ConflictException;

public class InitiatorConflictException extends ConflictException {
    public InitiatorConflictException() {
        super("Event initiator can't make a request for their own event");
    }
}
