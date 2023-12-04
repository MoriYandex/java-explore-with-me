package ru.practicum.main.event.exception;

import ru.practicum.main.exception.NotFoundException;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException(Long eventId) {
        super(String.format("Event with id=%d was not found", eventId));
    }
}
