package ru.main.event.service;

import org.springframework.data.domain.PageRequest;
import ru.main.event.dto.UpdateEventRequest;
import ru.main.event.dto.EventFullDto;
import ru.main.event.dto.EventShortDto;
import ru.main.event.dto.NewEventDto;
import ru.main.event.enums.EventSort;
import ru.main.event.enums.EventState;
import ru.main.event.enums.EventStateAdminAction;
import ru.main.event.enums.EventStateUserAction;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getUserEvents(Long userId, PageRequest pageRequest);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventRequest<EventStateUserAction> updateEventRequest);

    List<EventFullDto> getEventsByAdmin(List<Long> users,
                                        List<EventState> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        PageRequest pageRequest);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventRequest<EventStateAdminAction> updateEventRequest);

    List<EventShortDto> getPublicEvents(
            String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Boolean onlyAvailable, EventSort sort, Integer from, Integer size, HttpServletRequest request
    );

    EventFullDto getPublicEventById(Long id, HttpServletRequest request);

    void addEventRating(Long userId, Long eventId, Boolean isPositive);

    void deleteEventRating(Long userId, Long eventId);
}
