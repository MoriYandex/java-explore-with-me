package ru.main.request.exception;

import ru.main.exception.ConflictException;

public class ParticipantLimitConflictException extends ConflictException {
    public ParticipantLimitConflictException() {
        super("The participant limit has been reached");
    }
}
