package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.main.event.dto.EventFullDto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Изменение информации о подборке событий. Если поле в запросе не указано (равно null) -
 * значит изменение этих данных не требуется.
 */
@Data
@Builder
@AllArgsConstructor
public class UpdateCompilationDto implements Serializable {
    /**
     * Список идентификаторов ${@link EventFullDto}
     */
    private List<Long> events;
    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;
    /**
     * Заголовок подборки
     */
    @Size(min = 1, max = 50)
    private String title;
}
