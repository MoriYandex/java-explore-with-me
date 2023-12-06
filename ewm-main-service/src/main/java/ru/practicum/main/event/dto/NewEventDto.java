package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.location.dto.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Новое событие
 */
@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class NewEventDto {
    /**
     * Краткое описание
     */
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    /**
     * Категория {@link CategoryDto}
     */
    @NotNull
    @Positive
    private Long category;
    /**
     * Полное описание события
     */
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    /**
     * Дата и время на которые намечено событие
     */
    @NotNull
    @Future
    private LocalDateTime eventDate;
    /**
     * Локация проведения события {@link LocationDto}
     */
    @NotNull
    private LocationDto location;
    /**
     * Нужно ли оплачивать участие
     */
    @Builder.Default
    private Boolean paid = false;
    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Builder.Default
    @PositiveOrZero
    private Integer participantLimit = 0;
    /**
     * Нужна ли пре-модерация заявок на участие
     */
    @Builder.Default
    private Boolean requestModeration = true;
    /**
     * Заголовок
     */
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
