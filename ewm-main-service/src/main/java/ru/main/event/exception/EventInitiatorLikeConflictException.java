package ru.main.event.exception;

import ru.main.exception.ConflictException;

public class EventInitiatorLikeConflictException extends ConflictException {
    public EventInitiatorLikeConflictException() {
        super("The initiator cannot change the rating of his event");
    }
}
