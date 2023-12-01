package ru.practicum.statsservice.service;

import dto.EndpointHit;
import dto.ViewStats;

import java.util.List;

public interface StatsService {
    EndpointHit add(EndpointHit endpointHit);

    List<ViewStats> get(String start, String end, String[] uris, Boolean unique);

    EndpointHit getById(Long id);
}
