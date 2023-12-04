package ru.main.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные для добавления новой категории
 */
@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class NewCategoryDto implements Serializable {
    /**
     * Название категории
     */
    @NotBlank
    @Size(min = 1, max = 512)
    private String name;
}
