package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.enums.EventState;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * Категория {@link CategoryDto}
     */
    private CategoryDto category;
    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private Integer confirmedRequests;
    /**
     * Дата и время создания события
     */
    private LocalDateTime createdOn;
    /**
     * Полное описание события
     */
    private String description;
    /**
     * Дата и время на которые намечено событие
     */
    private LocalDateTime eventDate;
    /**
     * Инициатор события {@link UserShortDto}
     */
    private UserShortDto initiator;
    /**
     * Локация проведения события {@link LocationDto}
     */
    private LocationDto location;
    /**
     * Нужно ли оплачивать участие
     */
    private Boolean paid;
    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private Integer participantLimit;
    /**
     * Дата и время публикации события
     */
    private LocalDateTime publishedOn;
    /**
     * Нужна ли пре-модерация заявок на участие
     */
    private Boolean requestModeration;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Жизненный цикл события
     */
    private EventState state;
    /**
     * Количество просмотрев события
     */
    private Long views;
    /**
     * Рейтинг события
     */
    private Integer rating;
}
