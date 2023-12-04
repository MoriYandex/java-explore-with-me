package ru.main.event.exception;

import ru.main.exception.ConflictException;

public class EventStatusConflictException extends ConflictException {
    public EventStatusConflictException() {
        super("Only pending or canceled events can be changed");
    }
}
