package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 */
@Value
@Builder
@AllArgsConstructor
public class RequestsListsDto {
    /**
     * Подтвержденные заявки
     */
    List<RequestDto> confirmedRequests;
    /**
     * Отклоненные заявки
     */
    List<RequestDto> rejectedRequests;
}
