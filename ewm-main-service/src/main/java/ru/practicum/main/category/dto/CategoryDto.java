package ru.practicum.main.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Категория
 */
@Data
@Builder
@AllArgsConstructor
public class CategoryDto implements Serializable {
    /**
     * Идентификатор категории
     */
    private Long id;
    /**
     * Название категории
     */
    private String name;
}
