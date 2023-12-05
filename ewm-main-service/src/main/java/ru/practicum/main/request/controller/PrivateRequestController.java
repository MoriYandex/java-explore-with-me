package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.dto.RequestsListsDto;
import ru.practicum.main.request.dto.UpdateRequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping("/requests")
    List<RequestDto> get(@PathVariable Long userId) {
        return requestService.get(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/requests")
    RequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.create(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    RequestDto cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancel(userId, requestId);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<RequestDto> getByEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return requestService.getByEvent(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    public RequestsListsDto updateByEvent(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @RequestBody UpdateRequestDto updateRequestDto) {
        return requestService.updateByEvent(userId, eventId, updateRequestDto);
    }
}
