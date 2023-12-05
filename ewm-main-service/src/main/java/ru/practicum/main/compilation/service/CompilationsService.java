package ru.practicum.main.compilation.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;

import java.util.List;

@Service
public interface CompilationsService {
    List<CompilationDto> get(Boolean pinned, PageRequest pageRequest);

    CompilationDto getById(Long compilationId);

    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long compilationId);

    CompilationDto update(Long compilationId, UpdateCompilationDto updateCompilationDto);
}
