package ru.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.main.category.dto.CategoryDto;
import ru.main.user.dto.UserShortDto;

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
    private transient CategoryDto category;
    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private Integer confirmedRequests;
    /**
     * Дата и время на которые намечено событие
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    /**
     * Инициатор события {@link UserShortDto}
     */
    private transient UserShortDto initiator;
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
