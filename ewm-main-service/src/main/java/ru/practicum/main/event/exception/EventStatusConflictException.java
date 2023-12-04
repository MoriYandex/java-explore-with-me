package ru.practicum.main.event.exception;

import ru.practicum.main.exception.ConflictException;

public class EventStatusConflictException extends ConflictException {
    public EventStatusConflictException() {
        super("Only pending or canceled events can be changed");
    }
}
