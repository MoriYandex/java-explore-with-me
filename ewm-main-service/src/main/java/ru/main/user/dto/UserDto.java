package ru.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Пользователь
 */
@Data
@Builder
@AllArgsConstructor
public class UserDto implements Serializable {
    /**
     * Идентификатор
     */
    private Long id;
    /**
     * Почтовый адрес
     */
    private String email;
    /**
     * Имя
     */
    private String name;
    /**
     * Рейтинг пользователя
     */
    private Integer rating;
}
