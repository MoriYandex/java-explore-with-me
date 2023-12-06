package ru.practicum.stats.service;

import dto.EndpointHit;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.stats.exception.NotFoundException;
import ru.practicum.stats.exception.ValidationException;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.mapper.StatMapper;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ViewStats> get(String start, String end, List<String> uris, Boolean unique) {
        log.info("Получение статистики по параметрам: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        try {
            LocalDateTime startDate = LocalDateTime.parse(start, EndpointHitMapper.DATE_TIME_FORMATTER);
            LocalDateTime endDate = LocalDateTime.parse(end, EndpointHitMapper.DATE_TIME_FORMATTER);
            if (endDate.isBefore(startDate))
                throw new ValidationException("Дата начала диапазона должна быть ранее даты окончания");
            if (unique == null || !unique)
                return hitRepository.getStats(startDate, endDate, uris)
                        .stream().map(StatMapper::toViewStats)
                        .collect(Collectors.toList());
            else
                return hitRepository.getUniqueStats(startDate, endDate, uris)
                        .stream().map(StatMapper::toViewStats)
                        .collect(Collectors.toList());
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
