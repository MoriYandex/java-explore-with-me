package ru.practicum.main.user.exception;

import ru.practicum.main.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id=%d was not found", userId));
    }
}
