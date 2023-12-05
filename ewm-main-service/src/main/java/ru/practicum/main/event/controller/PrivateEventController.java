package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.enums.EventStateUserAction;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getByUser(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                         @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return eventService.getByUser(userId, PageRequest.of(from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody @Validated NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByUserById(@PathVariable Long userId,
                                      @PathVariable Long eventId) {
        return eventService.getByUserById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateByUser(@PathVariable Long userId,
                                     @PathVariable Long eventId,
                                     @RequestBody @Valid UpdateEventDto<EventStateUserAction> updateEventDto) {
        return eventService.updateByUser(userId, eventId, updateEventDto);
    }

    @PutMapping("/{eventId}/rating")
    public void addRating(@PathVariable Long userId,
                          @PathVariable Long eventId,
                          @RequestParam Boolean isPositive) {
        eventService.addRating(userId, eventId, isPositive);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{eventId}/rating")
    public void deleteRating(@PathVariable Long userId,
                             @PathVariable Long eventId) {
        eventService.deleteRating(userId, eventId);
    }
}