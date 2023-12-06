package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto implements Serializable {
    /**
     * Список идентификаторов событий входящих в подборку
     */
    @NotNull
    @Builder.Default
    private Set<Long> events = new HashSet<>();
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
    @Size(min = 1, max = 50)
    private String title;
}
