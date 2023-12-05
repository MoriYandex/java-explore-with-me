package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Пользователь (короткая информация)
 */
@Data
@Builder
@AllArgsConstructor
public class UserShortDto implements Serializable {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Имя
     */
    private String name;
}
