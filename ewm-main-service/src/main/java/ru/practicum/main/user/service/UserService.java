package ru.practicum.main.user.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.user.dto.NewUserDto;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get(List<Long> ids, PageRequest pageRequest);

    UserDto create(NewUserDto newUserDto);

    void delete(Long userId);
}
