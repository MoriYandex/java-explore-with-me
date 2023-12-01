package service;

import client.StatsClient;
import dto.EndpointHit;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsClientServiceImpl implements StatsClientService{
    private final StatsClient statsClient;
    @Override
    public List<ViewStats> getStats(String start, String end, String[] uris, Boolean unique) {
        return statsClient.getStats(start, end, uris, unique);
    }

    @Override
    public EndpointHit getById(Long id) {
        return statsClient.getById(id);
    }
}
