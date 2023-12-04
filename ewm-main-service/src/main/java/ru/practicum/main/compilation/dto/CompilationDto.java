package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.main.event.dto.EventShortDto;

import java.io.Serializable;
import java.util.List;

/**
 * Подборка событий
 */
@Data
@Builder
@AllArgsConstructor
public class CompilationDto implements Serializable {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Список {@link EventShortDto}
     */
    transient List<EventShortDto> events;
    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;
    /**
     * Заголовок подборки
     */
    private String title;
}
