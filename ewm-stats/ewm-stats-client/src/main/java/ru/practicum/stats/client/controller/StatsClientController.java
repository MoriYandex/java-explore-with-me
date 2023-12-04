package ru.practicum.stats.client.controller;

import dto.EndpointHit;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.client.service.StatsClientService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class StatsClientController {
    private final StatsClientService statsClientService;

    @GetMapping()
    public List<ViewStats> getStats(@RequestParam(name = "start") String start,
                                    @RequestParam(name = "end") String end,
                                    @RequestParam(name = "uris", required = false) String[] uris,
                                    @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique) {
        return statsClientService.getStats(start, end, Arrays.asList(uris), unique);
    }

    @GetMapping(path = "/{id}")
    public EndpointHit getById(@PathVariable(name = "id") Long id) {
        return statsClientService.getById(id);
    }
}
