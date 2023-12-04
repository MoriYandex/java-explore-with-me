package ru.practicum.main.event.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;

@Slf4j
@UtilityClass
public class SharedEventRequests {
    public static Event checkAndReturnEvent(EventRepository eventRepository, Long eventId) {
        log.info("SharedEventRequests check event. Event id: {}", eventId);
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
