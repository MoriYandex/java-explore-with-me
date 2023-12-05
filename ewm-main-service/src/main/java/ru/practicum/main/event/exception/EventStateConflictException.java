package ru.practicum.main.event.exception;

import ru.practicum.main.event.enums.EventStateAdminAction;
import ru.practicum.main.event.enums.EventStateUserAction;
import ru.practicum.main.exception.ConflictException;

import java.util.Arrays;

public class EventStateConflictException extends ConflictException {
    private static final String MESSAGE =
            "Field: state. Error: The field can only contain values: [%s]. But the current value is: %s";

    public EventStateConflictException(EventStateAdminAction state) {
        super(String.format(
                MESSAGE,
                Arrays.toString(EventStateAdminAction.values()),
                state
        ));
    }

    public EventStateConflictException(EventStateUserAction state) {
        super(String.format(
                MESSAGE,
                Arrays.toString(EventStateUserAction.values()),
                state
        ));
    }
}

