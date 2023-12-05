package ru.practicum.main.user.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.user.dto.NewUserRequestDto;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, PageRequest pageRequest);

    UserDto createUser(NewUserRequestDto newUserRequestDto);

    void deleteUser(Long userId);
}
