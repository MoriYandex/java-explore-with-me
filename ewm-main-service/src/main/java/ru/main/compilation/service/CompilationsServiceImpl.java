package ru.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.main.category.mapper.CategoryMapper;
import ru.main.compilation.dto.CompilationDto;
import ru.main.compilation.dto.NewCompilationDto;
import ru.main.event.repository.EventRepository;
import ru.main.exception.ConflictException;
import ru.main.compilation.dto.UpdateCompilationRequestDto;
import ru.main.compilation.exception.CompilationNotFoundException;
import ru.main.compilation.mapper.CompilationMapper;
import ru.main.compilation.model.Compilation;
import ru.main.compilation.repository.CompilationRepository;
import ru.main.event.dto.EventShortDto;
import ru.main.event.mapper.EventMapper;
import ru.main.event.model.Event;
import ru.main.user.mapper.UserMapper;

import java.util.List;
import java.util.Objects;
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
    public List<CompilationDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        List<Compilation> compilations = compilationRepository.findByPinned(pinned, pageRequest);
        log.info("Get compilations. Pinned: {}, page request: {}, compilations: {}", pinned, pageRequest, compilations);
        return compilations
                .stream().map(compilation ->
                        CompilationMapper.toDto(
                                compilation,
                                parseEventsToDto(compilation.getEvents())
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compilationId) {
        Compilation compilation = checkAndReturnCompilation(compilationId);
        return CompilationMapper.toDto(compilation, parseEventsToDto(compilation.getEvents()));
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        if (Objects.isNull(newCompilationDto)) {
            throw new ConflictException("Request body is empty");
        }
        Set<Event> events = eventRepository.getByIdIn(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        log.info("Create compilation. NewCompilationDto: {}, compilation: {}, events: {}", newCompilationDto, compilation, events);
        return CompilationMapper.toDto(compilationRepository.save(compilation), parseEventsToDto(events));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compilationId) {
        log.info("Delete compilation. Compilation id: {}", compilationId);
        compilationRepository.delete(checkAndReturnCompilation(compilationId));
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compilationId, UpdateCompilationRequestDto updateCompilation) {
        log.info("Update compilation. Compilation id: {}, updateCompilationRequestDto: {}", compilationId, updateCompilation);
        Compilation compilation = checkAndReturnCompilation(compilationId);
        Optional.ofNullable(updateCompilation.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(updateCompilation.getPinned()).ifPresent(compilation::setPinned);
        Optional.ofNullable(updateCompilation.getEvents())
                .ifPresent(eventIds -> compilation.setEvents(eventRepository.getByIdIn(eventIds)));
        log.info("Update compilation. Updated compilation: {}", compilation);
        return CompilationMapper.toDto(compilationRepository.save(compilation), null);
    }

    private Compilation checkAndReturnCompilation(Long compilationId) {
        log.info("Compilation find by id. Compilation id: {}", compilationId);
        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
    }

    private List<EventShortDto> parseEventsToDto(Set<Event> events) {
        return events.stream().map(event ->
                EventMapper.toShortDto(
                        event,
                        CategoryMapper.toDto(event.getCategory()),
                        UserMapper.toShortDto(event.getInitiator())
                )).collect(Collectors.toList());
    }
}
