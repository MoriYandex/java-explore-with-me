package ru.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.main.category.dto.CategoryDto;
import ru.main.location.dto.LocationDto;
import ru.main.user.dto.UserShortDto;
import ru.main.event.enums.EventState;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    /**
     * Полное описание события
     */
    private String description;
    /**
     * Дата и время на которые намечено событие
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
    private Integer views;
    /**
     * Рейтинг события
     */
    private Integer rating;
}
