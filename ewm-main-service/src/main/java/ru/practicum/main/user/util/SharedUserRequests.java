package ru.practicum.main.user.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

@Slf4j
@UtilityClass
public class SharedUserRequests {
    public static User checkAndReturnUser(UserRepository userRepository, Long userId) {
        log.info("SharedUserRequests check user. User id: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
