package ru.practicum.statsservice.service;

import dto.EndpointHit;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.statsservice.exception.NotFoundException;
import ru.practicum.statsservice.exception.ValidationException;
import ru.practicum.statsservice.mapper.EndpointHitMapper;
import ru.practicum.statsservice.model.Hit;
import ru.practicum.statsservice.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final HitRepository hitRepository;

    @Override
    public EndpointHit add(EndpointHit endpointHit) {
        log.info("Добавление данных запроса: {}", endpointHit);
        Hit hit = EndpointHitMapper.toHit(endpointHit);
        return EndpointHitMapper.toEndpointHit(hitRepository.saveAndFlush(hit));
    }

    @Override
    public List<ViewStats> get(String start, String end, String[] uris, Boolean unique) {
        log.info("Получение статистики по параметрам: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        try {
            List<ViewStats> result;
            LocalDateTime startDate = LocalDateTime.parse(start, EndpointHitMapper.DATE_TIME_FORMATTER);
            LocalDateTime endDate = LocalDateTime.parse(end, EndpointHitMapper.DATE_TIME_FORMATTER);
            if (uris == null || uris.length == 0)
                if (unique == null || !unique)
                    result = hitRepository.getStats(startDate, endDate);
                else
                    result = hitRepository.getUniqueStats(startDate, endDate);
            else if (unique == null || !unique)
                result = hitRepository.getStats(startDate, endDate, String.join(", ", uris));
            else
                result = hitRepository.getUniqueStats(startDate, endDate, String.join(", ", uris));
            return result;
        } catch (DateTimeParseException e) {
            throw new ValidationException("Неверный формат даты начала или окончания диапазона статистики: " + e.getMessage());
        }
    }

    @Override
    public EndpointHit getById(Long id) {
        log.info("Получение данных запроса");
        Hit resultHit = hitRepository.findById(id).orElse(null);
        if (resultHit == null)
            throw new NotFoundException("Не найден запрос по идентификатору " + id);
        return EndpointHitMapper.toEndpointHit(resultHit);
    }
}
