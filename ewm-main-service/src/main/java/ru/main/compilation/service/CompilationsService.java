package ru.main.compilation.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.main.compilation.dto.CompilationDto;
import ru.main.compilation.dto.NewCompilationDto;
import ru.main.compilation.dto.UpdateCompilationRequestDto;

import java.util.List;

@Service
public interface CompilationsService {
    List<CompilationDto> getCompilations(Boolean pinned, PageRequest pageRequest);

    CompilationDto getCompilationById(Long compilationId);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compilationId);

    CompilationDto updateCompilation(Long compilationId, UpdateCompilationRequestDto updateCompilationRequestDto);
}
