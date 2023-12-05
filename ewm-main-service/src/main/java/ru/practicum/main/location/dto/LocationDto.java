package ru.practicum.main.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Широта и долгота места проведения события
 */
@Data
@Builder
@AllArgsConstructor
public class LocationDto implements Serializable {
    /**
     * Широта
     */
    private Float lat;
    /**
     * Долгота
     */
    private Float lon;
}
