package ru.practicum.main.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.enums.EventSort;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.enums.EventStateAdminAction;
import ru.practicum.main.event.enums.EventStateUserAction;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getByUser(Long userId, PageRequest pageRequest);

    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto getByUserById(Long userId, Long eventId);

    EventFullDto updateByUser(Long userId, Long eventId, UpdateEventDto<EventStateUserAction> updateEventDto);

    List<EventFullDto> getByAdmin(List<Long> users,
                                  List<EventState> states,
                                  List<Long> categories,
                                  LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  PageRequest pageRequest);

    EventFullDto updateByAdmin(Long eventId, UpdateEventDto<EventStateAdminAction> updateEventDto);

    List<EventShortDto> getPublic(
            String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable, EventSort sort, Integer from, Integer size, HttpServletRequest request
    );

    EventFullDto getPublicById(Long id, HttpServletRequest request);

    void addRating(Long userId, Long eventId, Boolean isPositive);

    void deleteRating(Long userId, Long eventId);
}
