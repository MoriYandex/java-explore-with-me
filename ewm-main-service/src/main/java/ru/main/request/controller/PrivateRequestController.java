package ru.main.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.main.request.dto.EventRequestStatusUpdateRequest;
import ru.main.request.dto.ParticipationRequestDto;
import ru.main.request.service.RequestService;
import ru.main.request.dto.EventRequestStatusUpdateResult;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping("/requests")
    List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        return requestService.getUserRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/requests")
    ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.createUserRequest(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    ParticipationRequestDto cancelUserRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancelUserRequest(userId, requestId);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return requestService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequest(@PathVariable Long userId,
                                                                 @PathVariable Long eventId,
                                                                 @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return requestService.updateUserEventRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
