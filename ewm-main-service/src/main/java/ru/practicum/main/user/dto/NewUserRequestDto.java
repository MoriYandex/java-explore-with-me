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
public class NewUserRequestDto implements Serializable {
    /**
     * Почтовый адрес
     */
    @NotNull
    @Email
    @Size(min = 1, max = 256)
    private String email;
    /**
     * Имя
     */
    @NotBlank
    @Size(min = 1, max = 256)
    private String name;
}
