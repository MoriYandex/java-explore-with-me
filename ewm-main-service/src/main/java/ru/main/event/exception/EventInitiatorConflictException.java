package ru.main.event.exception;

import ru.main.exception.ConflictException;

public class EventInitiatorConflictException extends ConflictException {
    public EventInitiatorConflictException() {
        super("Only the initiator can update an event");
    }
}
