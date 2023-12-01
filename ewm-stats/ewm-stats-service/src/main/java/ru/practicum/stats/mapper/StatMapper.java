package ru.practicum.stats.mapper;

import dto.ViewStats;
import ru.practicum.stats.model.Stat;

public class StatMapper {
    public static ViewStats toViewStats(Stat stat) {
        return ViewStats.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHits())
                .build();
    }
}
