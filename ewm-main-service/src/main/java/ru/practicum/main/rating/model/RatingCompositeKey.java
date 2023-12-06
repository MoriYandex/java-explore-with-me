package ru.practicum.main.rating.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RatingCompositeKey implements Serializable {
    private Long userId;
    private Long eventId;
}
