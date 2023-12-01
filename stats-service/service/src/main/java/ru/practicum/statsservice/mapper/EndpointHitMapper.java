package ru.practicum.statsservice.mapper;

import dto.EndpointHit;
import ru.practicum.statsservice.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHit toEndpointHit(Hit hit) {
        return EndpointHit.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getHitDate().format(DATE_TIME_FORMATTER))
                .build();
    }

    public static Hit toHit(EndpointHit endpointHit) {
        return Hit.builder()
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .hitDate(LocalDateTime.parse(endpointHit.getTimestamp(), DATE_TIME_FORMATTER))
                .build();
    }
}
