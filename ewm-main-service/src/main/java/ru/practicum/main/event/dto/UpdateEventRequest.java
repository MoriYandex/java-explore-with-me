package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.main.location.dto.LocationDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) -
 * значит изменение этих данных не требуется.
 */
@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class UpdateEventRequest<T> {
    /**
     * Новая аннотация
     */
    @Size(min = 20, max = 2000)
    private String annotation;
    /**
     * Новая категория
     */
    private Long category;
    /**
     * Новое описание
     */
    @Size(min = 20, max = 7000)
    private String description;
    /**
     * Новые дата и время на которые намечено событие.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    /**
     * Новая локация {@link LocationDto}
     */
    private LocationDto location;
    /**
     * Новое значение флага о платности мероприятия
     */
    private Boolean paid;
    /**
     * Новый лимит пользователей
     */
    private Integer participantLimit;
    /**
     * Нужна ли премодерация заявок на участие
     */
    private Boolean requestModeration;
    /**
     * Новое состояние события
     */
    private T stateAction;
    /**
     * Новый заголовок
     */
    @Size(min = 3, max = 120)
    private String title;
}
