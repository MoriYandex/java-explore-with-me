package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.request.enums.NewRequestStatus;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
@Value
@Builder
@AllArgsConstructor
public class UpdateRequestDto {
    /**
     * Новый статус запроса на участие в событии текущего пользователя
     */
    List<Long> requestIds;
    /**
     * Новый статус запроса на участие в событии текущего пользователя
     */
    NewRequestStatus status;
}
