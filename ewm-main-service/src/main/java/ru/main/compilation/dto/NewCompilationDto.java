package ru.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class NewCompilationDto implements Serializable {
    /**
     * Список идентификаторов событий входящих в подборку
     */
    @NotNull
    private transient Set<Long> events;
    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @NotNull
    @Builder.Default
    private Boolean pinned = false;
    /**
     * Заголовок подборки
     */
    @NotBlank
    @Size(min = 1, max = 512)
    private String title;
}
