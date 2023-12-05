package ru.practicum.main.util;

import dto.EndpointHit;
import dto.ViewStats;
import lombok.experimental.UtilityClass;
import ru.practicum.stats.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class StatsClientHelper {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void makePublicEndpointHit(StatsClient statsClient, HttpServletRequest request) {
        statsClient.postHit(EndpointHit.builder()
                .app("ewm-main-service")
                .timestamp(DATE_TIME_FORMATTER.format(LocalDateTime.now()))
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());
    }

    public static Map<String, Long> getViews(StatsClient statsClient,
                                             String start,
                                             String end,
                                             List<String> uris,
                                             Boolean unique) {
        List<ViewStats> stats = (List<ViewStats>) statsClient.getStats(start, end, uris, unique).getBody();
        return stats.stream().collect(Collectors.toMap(
                ViewStats::getUri,
                ViewStats::getHits));
    }
}
