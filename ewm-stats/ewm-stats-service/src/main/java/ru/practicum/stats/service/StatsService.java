package ru.practicum.stats.service;

import dto.EndpointHit;
import dto.ViewStats;

import java.util.List;

public interface StatsService {
    EndpointHit add(EndpointHit endpointHit);

    List<ViewStats> get(String start, String end, List<String> uris, Boolean unique);

    EndpointHit getById(Long id);
}
