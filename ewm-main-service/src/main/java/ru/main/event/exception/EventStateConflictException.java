package ru.main.event.exception;

import ru.main.event.enums.EventStateAdminAction;
import ru.main.event.enums.EventStateUserAction;
import ru.main.exception.ConflictException;

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

