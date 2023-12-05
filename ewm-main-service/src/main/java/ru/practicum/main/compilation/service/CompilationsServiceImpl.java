package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.main.compilation.exception.CompilationNotFoundException;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.user.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> get(Boolean pinned, PageRequest pageRequest) {
        List<Compilation> compilations = compilationRepository.findByPinned(pinned, pageRequest);
        log.info("Get compilations. Pinned: {}, page request: {}, compilations: {}", pinned, pageRequest, compilations);
        return compilations
                .stream().map(compilation ->
                        CompilationMapper.toCompilationDto(
                                compilation,
                                parseEventsToDto(compilation.getEvents())
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long compilationId) {
        Compilation compilation = checkAndReturnCompilation(compilationId);
        return CompilationMapper.toCompilationDto(compilation, parseEventsToDto(compilation.getEvents()));
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Set<Event> events = eventRepository.getByIdIn(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, events);
        log.info("Create compilation. NewCompilationDto: {}, compilation: {}, events: {}", newCompilationDto, compilation, events);
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation), parseEventsToDto(events));
    }

    @Override
    @Transactional
    public void delete(Long compilationId) {
        log.info("Delete compilation. Compilation id: {}", compilationId);
        compilationRepository.delete(checkAndReturnCompilation(compilationId));
    }

    @Override
    @Transactional
    public CompilationDto update(Long compilationId, UpdateCompilationDto updateCompilation) {
        log.info("Update compilation. Compilation id: {}, updateCompilationRequestDto: {}", compilationId, updateCompilation);
        Compilation compilation = checkAndReturnCompilation(compilationId);
        Optional.ofNullable(updateCompilation.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(updateCompilation.getPinned()).ifPresent(compilation::setPinned);
        Optional.ofNullable(updateCompilation.getEvents())
                .ifPresent(eventIds -> compilation.setEvents(eventRepository.getByIdIn(eventIds)));
        log.info("Update compilation. Updated compilation: {}", compilation);
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation), null);
    }

    private Compilation checkAndReturnCompilation(Long compilationId) {
        log.info("Compilation find by id. Compilation id: {}", compilationId);
        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
    }

    private List<EventShortDto> parseEventsToDto(Set<Event> events) {
        return events.stream().map(event ->
                EventMapper.toEventShortDto(
                        event,
                        CategoryMapper.toCategoryDto(event.getCategory()),
                        UserMapper.toUserShortDto(event.getInitiator())
                )).collect(Collectors.toList());
    }
}
