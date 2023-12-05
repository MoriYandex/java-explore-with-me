package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.dto.RequestsListsDto;
import ru.practicum.main.request.dto.UpdateRequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> get(Long userId);

    RequestDto create(Long userId, Long eventId);

    RequestDto cancel(Long userId, Long requestId);

    List<RequestDto> getByEvent(Long userId, Long eventId);

    RequestsListsDto updateByEvent(Long userId, Long eventId, UpdateRequestDto updateRequestDto);
}
