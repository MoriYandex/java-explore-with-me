package ru.practicum.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.model.Request;

@UtilityClass
public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .event(request.getEvent().getId())
                .build();
    }
}
