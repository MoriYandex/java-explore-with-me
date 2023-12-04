package ru.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.main.event.dto.EventFullDto;

import java.io.Serializable;
import java.util.List;

/**
 * Изменение информации о подборке событий. Если поле в запросе не указано (равно null) -
 * значит изменение этих данных не требуется.
 */
@Data
@Builder
@AllArgsConstructor
public class UpdateCompilationRequestDto implements Serializable {
    /**
     * Список идентификаторов ${@link EventFullDto}
     */
    private transient List<Long> events;
    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;
    /**
     * Заголовок подборки
     */
    private String title;
}
