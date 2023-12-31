package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.enums.EventSort;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.event.enums.EventStateAdminAction;
import ru.practicum.main.event.enums.EventStateUserAction;
import ru.practicum.main.event.exception.*;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.ValidationException;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.location.mapper.LocationMapper;
import ru.practicum.main.location.model.Location;
import ru.practicum.main.location.repository.LocationRepository;
import ru.practicum.main.rating.model.Rating;
import ru.practicum.main.rating.repository.RatingRepository;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;
import ru.practicum.main.util.StatsClientHelper;
import ru.practicum.stats.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.main.util.RatingCalculator.calculateRating;
import static ru.practicum.main.util.SharedRequests.checkAndReturnCategory;
import static ru.practicum.main.util.SharedRequests.checkAndReturnUser;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final String EVENTS_PREFIX = "/events/";
    private final RatingRepository ratingRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatsClient statsClient;

    @Override
    public EventFullDto getPublicById(Long eventId, HttpServletRequest request) {
        log.info("Get public events by eventId: {}", eventId);
        StatsClientHelper.makePublicEndpointHit(statsClient, request);
        Map<String, Long> viewMap = StatsClientHelper.getViews(statsClient,
                "0001-01-01 00:00:01",
                "9999-12-31 23:59:59",
                List.of(request.getRequestURI()),
                true);
        EventFullDto result = parseToFullDtoWithMappers(eventRepository.findByIdAndPublished(eventId).orElseThrow(() -> new EventNotFoundException(eventId)));
        result.setViews(viewMap.get(request.getRequestURI()));
        return result;
    }

    @Override
    @Transactional
    public void addRating(Long userId, Long eventId, Boolean isPositive) {
        log.info("Add event rating. UserId: {}, eventId: {}, isPositive: {}", userId, eventId, isPositive);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        Long initiatorId = event.getInitiator().getId();
        if (initiatorId.equals(userId))
            throw new EventInitiatorLikeConflictException();
        ratingRepository.save(new Rating(userId, eventId, isPositive, initiatorId));
    }

    @Override
    @Transactional
    public void deleteRating(Long userId, Long eventId) {
        log.info("Delete event rating. UserId: {}, eventId: {}", userId, eventId);
        ratingRepository.deleteByUserIdAndEventId(userId, eventId);
    }

    @Override
    public List<EventFullDto> getByAdmin(List<Long> users,
                                         List<EventState> states,
                                         List<Long> categories,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         PageRequest pageRequest) {
        log.info("Get events by admin. Users: {}, states: {}, categories: {}, rangeStart: {}, rangeEnd: {}, pageRequest: {}",
                users, states, categories, rangeStart, rangeEnd, pageRequest);
        return eventRepository.getEventsForAdmin(users, states, categories, rangeStart, rangeEnd, pageRequest)
                .stream().map(this::parseToFullDtoWithMappers)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateByAdmin(Long eventId, UpdateEventDto<EventStateAdminAction> updateEventDto) {
        log.info("Update event by admin. EventId: {}, eventRequest: {}", eventId, updateEventDto);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        checkEventState(event.getState());
        updateEvent(updateEventDto, event);
        /* Обновление состояния */
        Optional.ofNullable(updateEventDto.getStateAction()).ifPresent(state -> {
            switch (state) {
                case PUBLISH_EVENT:
                    if (event.getState() != EventState.PENDING)
                        throw new EventStatusConflictException();
                    event.setState(EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case REJECT_EVENT:
                    event.setState(EventState.CANCELED);
                    break;
                default:
                    throw new EventStateConflictException(state);
            }
        });
        log.info("Updated event by admin: {}", event);
        return parseToFullDtoWithMappers(event);
    }

    @Override
    public List<EventShortDto> getByUser(Long userId, PageRequest pageRequest) {
        checkAndReturnUser(userRepository, userId);
        List<Event> events = eventRepository.getByInitiatorIdOrderByEventDateDesc(userId, pageRequest);
        log.info("Get user events. User id: {}, page request: {}, events: {}", userId, pageRequest, events);
        return events.stream().map(this::parseToShortDtoWithMappers).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        log.info("Create event. UserId: {}, NewEventDto: {}", userId, newEventDto);
        checkEventDate(newEventDto.getEventDate());
        User user = checkAndReturnUser(userRepository, userId);
        Location location = findOrCreateLocation(newEventDto.getLocation());
        Category category = checkAndReturnCategory(categoryRepository, newEventDto.getCategory());
        log.info("Create event. Found category: {}", category);
        Event event = EventMapper.toEvent(newEventDto, category, location, user);
        return parseToFullDtoWithMappers(eventRepository.save(event));
    }

    @Override
    public EventFullDto getByUserById(Long userId, Long eventId) {
        log.info("Get user event By Id. UserId: {}, eventId: {}", userId, eventId);
        return parseToFullDtoWithMappers(eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId)));
    }

    @Override
    @Transactional
    public EventFullDto updateByUser(Long userId, Long eventId, UpdateEventDto<EventStateUserAction> updateEventDto) {
        log.info("Update user event. UserId: {}, eventId: {}, UpdateEventUserRequest: {}", userId, eventId, updateEventDto);
        if (updateEventDto == null)
            return new EventFullDto();
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        checkEventInitiator(userId, event.getInitiator());
        checkEventState(event.getState());
        updateEvent(updateEventDto, event);
        /* Обновление состояния */
        Optional.ofNullable(updateEventDto.getStateAction()).ifPresent(state -> {
            switch (state) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new EventStateConflictException(state);
            }
        });
        log.info("Updated event by user: {}", event);
        return parseToFullDtoWithMappers(event);
    }

    @Override
    public List<EventShortDto> getPublic(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         EventSort sort,
                                         Integer from,
                                         Integer size,
                                         HttpServletRequest request) {
        log.info("Get public events. Text: {}, categories: {}, paid: {}, rangeStart: {}, rangeEnd: {}, onlyAvailable: {}, sort: {}, from: {}, size: {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        if (rangeEnd.isBefore(rangeStart))
            throw new ValidationException("Дата начала диапазона не может быть позднее даты окончания!");
        StatsClientHelper.makePublicEndpointHit(statsClient, request);
        PageRequest pageRequest;
        List<Event> events;
        pageRequest = sort == EventSort.EVENT_DATE
                ? PageRequest.of(from, size, Sort.by("eventDate"))
                : PageRequest.of(from, size);
        events = Boolean.TRUE.equals(onlyAvailable)
                ? eventRepository.getAvailableEventsForUser(text, categories, paid, rangeStart, rangeEnd, pageRequest)
                : eventRepository.getEventsForUser(text, categories, paid, rangeStart, rangeEnd, pageRequest);
        if (Objects.nonNull(sort)) {
            switch (sort) {
                case VIEWS:
                    sortEventsByViews(events);
                    break;
                case EVENT_RATING:
                    sortEventByRating(events);
                    break;
                case USER_RATING:
                    sortEventsByUserRating(events);
                    break;
            }
        }
        return events.stream().map(this::parseToShortDtoWithMappers).collect(Collectors.toList());
    }

    /* Helpers */

    /**
     * Сортировка событий по рейтингу организатора
     *
     * @param events список событий
     */
    private void sortEventsByUserRating(List<Event> events) {
        events.sort((a, b) -> calculateRating(b.getInitiator().getRatings())
                .compareTo(calculateRating(a.getInitiator().getRatings())));
    }

    /**
     * Сортировка событий по рейтингу события
     *
     * @param events список событий
     */
    private void sortEventByRating(List<Event> events) {
        events.sort((a, b) -> calculateRating(b.getRatings()).compareTo(calculateRating(a.getRatings())));
    }

    /**
     * Сортировка событий по количеству просмотров
     *
     * @param events список событий
     */
    private void sortEventsByViews(List<Event> events) {
        Map<String, Long> stats = getEventStats(events);
        events.sort((a, b) -> stats.getOrDefault(EVENTS_PREFIX + b.getId(), 0L)
                .compareTo(stats.getOrDefault(EVENTS_PREFIX + a.getId(), 0L)));
    }

    /**
     * Получение просмотров с сервиса статистики
     *
     * @param events список событий
     * @return {@link Map} где ключ это url, а значение количество просмотров
     */
    private Map<String, Long> getEventStats(List<Event> events) {
        List<String> uris = events
                .stream()
                .map(event -> EVENTS_PREFIX + event.getId())
                .collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = LocalDateTime.now().minusYears(10).format(formatter);
        String end = LocalDateTime.now().plusYears(10).format(formatter);
        return StatsClientHelper.getViews(statsClient, start, end, uris, false);
    }

    /**
     * Проверка на то что событие еще не опубликовано
     *
     * @param state текущее состояние события
     * @throws EventDateConflictException если событие опубликовано
     */
    private void checkEventState(EventState state) {
        if (state == EventState.PUBLISHED)
            throw new EventStatusConflictException();
    }

    /**
     * Парсинг сущности к укороченному варианту данных события
     *
     * @param event {@link Event}
     * @return {@link EventShortDto}
     */
    private EventShortDto parseToShortDtoWithMappers(Event event) {
        return EventMapper.toEventShortDto(event, CategoryMapper.toCategoryDto(event.getCategory()), UserMapper.toUserShortDto(event.getInitiator()));
    }

    /**
     * Парсинг сущности к полному варианту данных события
     *
     * @param event {@link Event}
     * @return {@link EventFullDto}
     */
    private EventFullDto parseToFullDtoWithMappers(Event event) {
        return EventMapper.toEventFullDto(eventRepository.save(event),
                CategoryMapper.toCategoryDto(event.getCategory()),
                UserMapper.toUserShortDto(event.getInitiator()),
                LocationMapper.toLocationDto(event.getLocation()));
    }

    /**
     * Проверка на то что пользователь является инициатором события
     *
     * @param userId    идентификатор пользователя
     * @param initiator инициатор события
     * @throws EventInitiatorConflictException если пользователь не инициатор события
     */
    private void checkEventInitiator(Long userId, User initiator) {
        if (!Objects.equals(userId, initiator.getId()))
            throw new EventInitiatorConflictException();
    }

    /**
     * Проверка на то что дата и время на которые намечено событие не может быть раньше,
     * чем через два часа от текущего момента
     *
     * @param eventDate дата события
     * @throws EventDateConflictException если не прошла валидация по дате события
     */
    private void checkEventDate(LocalDateTime eventDate) {
        if (LocalDateTime.now().plusHours(2).isAfter(eventDate))
            throw new EventDateConflictException(eventDate);
    }

    /**
     * Вынес одинаковые для пользователя и админа поля для обновления
     *
     * @param updateEventDto новые значения для события
     * @param event          событие для записи значений
     */
    private void updateEvent(UpdateEventDto<?> updateEventDto, Event event) {
        /* Обновление аннотации */
        Optional.ofNullable(updateEventDto.getAnnotation()).ifPresent(event::setAnnotation);
        /* Обновление категории */
        Optional.ofNullable(updateEventDto.getCategory())
                .ifPresent(categoryId -> event.setCategory(checkAndReturnCategory(categoryRepository, categoryId)));
        /* Обновление описания */
        Optional.ofNullable(updateEventDto.getDescription()).ifPresent(event::setDescription);
        /* Обновление даты события */
        Optional.ofNullable(updateEventDto.getEventDate()).ifPresent(eventDate -> {
            checkEventDate(eventDate);
            event.setEventDate(eventDate);
        });
        /* Обновление локации */
        Optional.ofNullable(updateEventDto.getLocation())
                .ifPresent(locationDto -> event.setLocation(findOrCreateLocation(locationDto)));
        /* Обновление платности мероприятия */
        Optional.ofNullable(updateEventDto.getPaid()).ifPresent(event::setPaid);
        /* Обновление лимита посетителей */
        Optional.ofNullable(updateEventDto.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        /* Обновление статуса модерации */
        Optional.ofNullable(updateEventDto.getRequestModeration()).ifPresent(event::setRequestModeration);
        /* Обновление заголовка */
        Optional.ofNullable(updateEventDto.getTitle()).ifPresent(event::setTitle);
    }

    private Location findOrCreateLocation(LocationDto locationDto) {
        log.info("SharedLocationRequests find or create location. Location: {}", locationDto);
        Float lat = locationDto.getLat();
        Float lon = locationDto.getLon();
        return locationRepository.findByLocation(lat, lon)
                .orElse(locationRepository.save(LocationMapper.toLocation(locationDto)));
    }
}
