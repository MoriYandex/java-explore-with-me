package ru.main.user.service;

import org.springframework.data.domain.PageRequest;
import ru.main.user.dto.NewUserRequestDto;
import ru.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, PageRequest pageRequest);

    UserDto createUser(NewUserRequestDto newUserRequestDto);

    void deleteUser(Long userId);
}
