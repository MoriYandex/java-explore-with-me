package ru.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.main.request.dto.ParticipationRequestDto;
import ru.main.request.model.Request;

@UtilityClass
public class RequestMapper {
    public static ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .event(request.getEvent().getId())
                .build();
    }
}
