package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные нового пользователя
 */
@Data
@Builder
@AllArgsConstructor
public class NewUserDto implements Serializable {
    /**
     * Почтовый адрес
     */
    @NotNull
    @Email
    @Size(min = 6, max = 254)
    private String email;
    /**
     * Имя
     */
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
