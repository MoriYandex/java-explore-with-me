package ru.practicum.stats;

import dto.EndpointHit;
import dto.ViewStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "stats-client", url = "${stats-server.url}")
public interface StatsClient {
    @GetMapping(path = "/stats")
    List<ViewStats> getStats(@RequestParam(name = "start") String start,
                             @RequestParam(name = "end") String end,
                             @RequestParam(name = "uri", required = false) List<String> uris,
                             @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique);

    @PostMapping(path = "/hit")
    EndpointHit postHit(@RequestBody EndpointHit hit);
}
