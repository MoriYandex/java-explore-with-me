package ru.practicum.main.rating.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class RatingCompositeKey implements Serializable {
    private Long userId;
    private Long eventId;
}
