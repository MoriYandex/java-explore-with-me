package ru.main.request.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestCount {
    private final Long eventId;
    private final Integer count;
}
