package client;

import dto.EndpointHit;
import dto.ViewStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stats-client", url = "${stats-server.url}")
public interface StatsClient {
    @GetMapping(path = "/stats")
    List<ViewStats> getStats(@RequestParam(name = "start") String start,
                             @RequestParam(name = "end") String end,
                             @RequestParam(name = "uri", required = false) String[] uris,
                             @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique);

    @GetMapping(path = "/stats/{id}")
    EndpointHit getById(@PathVariable(name = "id") Long id);
}
