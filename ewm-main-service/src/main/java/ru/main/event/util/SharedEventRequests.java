package ru.main.event.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.main.event.repository.EventRepository;
import ru.main.event.exception.EventNotFoundException;
import ru.main.event.model.Event;

@Slf4j
@UtilityClass
public class SharedEventRequests {
    public static Event checkAndReturnEvent(EventRepository eventRepository, Long eventId) {
        log.info("SharedEventRequests check event. Event id: {}", eventId);
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
