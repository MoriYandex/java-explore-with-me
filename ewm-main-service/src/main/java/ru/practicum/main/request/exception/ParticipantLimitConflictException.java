package ru.practicum.main.request.exception;

import ru.practicum.main.exception.ConflictException;

public class ParticipantLimitConflictException extends ConflictException {
    public ParticipantLimitConflictException() {
        super("The participant limit has been reached");
    }
}
