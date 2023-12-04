package ru.main.request.dto;

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
public class EventRequestStatusUpdateResult {
    /**
     * Подтвержденные заявки
     */
    List<ParticipationRequestDto> confirmedRequests;
    /**
     * Отклоненные заявки
     */
    List<ParticipationRequestDto> rejectedRequests;
}
