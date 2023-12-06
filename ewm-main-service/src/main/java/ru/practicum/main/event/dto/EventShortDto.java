package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class EventShortDto implements Serializable {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * {@link CategoryDto}
     */
    private CategoryDto category;
    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private Integer confirmedRequests;
    /**
     * Дата и время на которые намечено событие
     */
    private LocalDateTime eventDate;
    /**
     * Инициатор события {@link UserShortDto}
     */
    private UserShortDto initiator;
    /**
     * Нужно ли оплачивать участие
     */
    private Boolean paid;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Количество просмотрев события
     */
    private Integer views;
    /**
     * Рейтинг события
     */
    private Integer rating;
}
