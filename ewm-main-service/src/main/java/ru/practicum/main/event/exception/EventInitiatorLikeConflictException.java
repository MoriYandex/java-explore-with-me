package ru.practicum.main.event.exception;

import ru.practicum.main.exception.ConflictException;

public class EventInitiatorLikeConflictException extends ConflictException {
    public EventInitiatorLikeConflictException() {
        super("The initiator cannot change the rating of his event");
    }
}
