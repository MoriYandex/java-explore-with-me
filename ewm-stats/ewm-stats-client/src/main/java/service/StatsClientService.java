package service;

import dto.EndpointHit;
import dto.ViewStats;

import java.util.List;

public interface StatsClientService {
    List<ViewStats> getStats(String start, String end, String[] uris, Boolean unique);
    EndpointHit getById(Long id);
}
