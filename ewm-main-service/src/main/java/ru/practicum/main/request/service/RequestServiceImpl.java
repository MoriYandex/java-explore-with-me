package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.exception.EventNotFoundException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.dto.RequestsListsDto;
import ru.practicum.main.request.dto.UpdateRequestDto;
import ru.practicum.main.request.enums.NewRequestStatus;
import ru.practicum.main.request.enums.RequestStatus;
import ru.practicum.main.request.exception.*;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repository.RequestRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.util.SharedRequests.checkAndReturnUser;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<RequestDto> get(Long userId) {
        checkAndReturnUser(userRepository, userId);
        List<Request> requests = requestRepository.findByRequesterId(userId);
        log.info("Get user requests. User id: {}, requests: {}", userId, requests);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestDto create(Long userId, Long eventId) {
        log.info("Create user request. User id: {}, eventId: {}", userId, eventId);
        Event event = checkAndReturnEvent(eventId);
        User user = checkAndReturnUser(userRepository, userId);
        if (Boolean.TRUE.equals(requestRepository.existsByEventIdAndRequesterId(event.getId(), userId)))
            throw new RequestAlreadyCreatedConflictException();
        if (event.getInitiator().getId().equals(userId))
            throw new InitiatorConflictException();
        if (event.getState() != EventState.PUBLISHED)
            throw new UnpublishedEventConflictException();
        if (event.getParticipantLimit() > 0 && event.getParticipants().size() >= event.getParticipantLimit())
            throw new ParticipantLimitConflictException();
        return RequestMapper.toRequestDto(requestRepository.save(Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .status((Boolean.TRUE.equals(event.getRequestModeration()) && event.getParticipantLimit() > 0) ? RequestStatus.PENDING : RequestStatus.CONFIRMED)
                .build()));
    }

    @Override
    @Transactional
    public RequestDto cancel(Long userId, Long requestId) {
        log.info("Cancel user request. UserId: {}, requestId: {}", userId, requestId);
        User user = checkAndReturnUser(userRepository, userId);
        Request request = checkAndReturnRequest(requestId);
        if (!user.getId().equals(request.getRequester().getId()))
            throw new RequestUserConflictException();
        log.info("Request: {} canceled by user: {}", request, user);
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public List<RequestDto> getByEvent(Long userId, Long eventId) {
        List<Request> requests = requestRepository.findByEventInitiatorIdAndEventId(userId, eventId);
        log.info("Get user event requests. UserId: {}, eventId: {}, requests: {}",
                userId, eventId, requests);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestsListsDto updateByEvent(Long userId, Long eventId, UpdateRequestDto updateRequestDto) {
        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();
        Event event = checkAndReturnEvent(eventId);
        if (event.getParticipants().size() >= event.getParticipantLimit())
            throw new ParticipantLimitConflictException();
        if (event.getState() != EventState.PUBLISHED)
            throw new UnpublishedEventConflictException();
        List<Request> requests = requestRepository.findAllById(updateRequestDto.getRequestIds());
        requests.forEach(request -> {
            if (Boolean.FALSE.equals(event.getRequestModeration()) || event.getParticipantLimit() == 0)
                return;
            if (updateRequestDto.getStatus() == NewRequestStatus.REJECTED) {
                request.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(RequestMapper.toRequestDto(request));
            }
            if (updateRequestDto.getStatus() == NewRequestStatus.CONFIRMED) {
                request.setStatus(RequestStatus.CONFIRMED);
                confirmedRequests.add(RequestMapper.toRequestDto(request));
            }
        });
        return RequestsListsDto.builder().confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests).build();
    }

    private Request checkAndReturnRequest(Long requestId) {
        log.info("Check request. Request id: {}", requestId);
        return requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException(requestId));
    }

    private Event checkAndReturnEvent(Long eventId) {
        log.info("SharedEventRequests check event. Event id: {}", eventId);
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
