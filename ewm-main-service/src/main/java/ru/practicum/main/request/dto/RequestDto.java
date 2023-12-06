package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.main.request.enums.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */
@Data
@Builder
@AllArgsConstructor
public class RequestDto implements Serializable {
    /**
     * Идентификатор заявки
     */
    private Long id;
    /**
     * Дата и время создания заявки
     */
    private LocalDateTime created;
    /**
     * Идентификатор события
     */
    private Long event;
    /**
     * Идентификатор пользователя, отправившего заявку
     */
    private Long requester;
    /**
     * Статус заявки
     */
    private RequestStatus status;
}
