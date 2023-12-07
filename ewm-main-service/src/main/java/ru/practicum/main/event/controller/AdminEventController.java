package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.enums.EventStateAdminAction;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getByAdmin(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<EventState> states,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                         LocalDateTime rangeStart,
                                         @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(50)}")
                                         LocalDateTime rangeEnd,
                                         @RequestParam(defaultValue = "0", required = false) Integer from,
                                         @RequestParam(defaultValue = "10", required = false) Integer size) {
        return eventService.getByAdmin(users, states, categories, rangeStart, rangeEnd, PageRequest.of(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateByAdmin(@PathVariable Long eventId,
                                      @RequestBody @Valid UpdateEventDto<EventStateAdminAction> updateEventDto) {
        return eventService.updateByAdmin(eventId, updateEventDto);
    }
}
