package ru.practicum.stats.controller;

import dto.EndpointHit;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping(path = "/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EndpointHit add(@RequestBody EndpointHit endpointHit) {
        return statsService.add(endpointHit);
    }

    @GetMapping(path = "/stats")
    public List<ViewStats> get(@RequestParam(name = "start") String start,
                               @RequestParam(name = "end") String end,
                               @RequestParam(name = "uris", required = false) String[] uris,
                               @RequestParam(name = "unique", required = false) Boolean unique) {
        return statsService.get(start, end, uris, unique);
    }

    @GetMapping(path = "/stats/{id}")
    public EndpointHit get(@PathVariable(name = "id") Long id) {
        return statsService.getById(id);
    }
}
