package ru.main.user.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.main.user.exception.UserNotFoundException;
import ru.main.user.model.User;
import ru.main.user.repository.UserRepository;

@Slf4j
@UtilityClass
public class SharedUserRequests {
    public static User checkAndReturnUser(UserRepository userRepository, Long userId) {
        log.info("SharedUserRequests check user. User id: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
