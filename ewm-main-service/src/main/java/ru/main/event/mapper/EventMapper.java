package ru.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.main.category.dto.CategoryDto;
import ru.main.category.model.Category;
import ru.main.event.dto.EventFullDto;
import ru.main.location.dto.LocationDto;
import ru.main.location.model.Location;
import ru.main.user.dto.UserShortDto;
import ru.main.user.model.User;
import ru.main.event.dto.EventShortDto;
import ru.main.event.dto.NewEventDto;
import ru.main.event.enums.EventState;
import ru.main.event.model.Event;
import ru.main.rating.util.RatingCalculator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@UtilityClass
public class EventMapper {
    public static Event toEntity(NewEventDto newEventDto, Category category, Location location, User initiator) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(location)
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .createdOn(LocalDateTime.now())
                .initiator(initiator)
                .state(EventState.PENDING)
                .build();
    }

    public static EventShortDto toShortDto(Event event, CategoryDto category, UserShortDto initiator) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(category)
                .confirmedRequests(Optional.ofNullable(event.getParticipants()).orElse(Set.of()).size())
                .eventDate(event.getEventDate())
                .initiator(initiator)
                .paid(event.getPaid())
                .title(event.getTitle())
                .rating(Optional.ofNullable(event.getRatings()).map(RatingCalculator::calculateRating).orElse(0))
                .build();
    }

    public static EventFullDto toDto(Event event, CategoryDto category, UserShortDto initiator, LocationDto location) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(category)
                .confirmedRequests(Optional.ofNullable(event.getParticipants()).orElse(Set.of()).size())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(initiator)
                .location(location)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .rating(Optional.ofNullable(event.getRatings()).map(RatingCalculator::calculateRating).orElse(0))
                .build();
    }
}
