package ru.main.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.main.compilation.dto.CompilationDto;
import ru.main.compilation.dto.NewCompilationDto;
import ru.main.compilation.model.Compilation;
import ru.main.event.dto.EventShortDto;
import ru.main.event.model.Event;

import java.util.List;
import java.util.Set;

@UtilityClass
public class CompilationMapper {
    public static Compilation toEntity(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .events(events)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .build();
    }

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(events)
                .build();
    }
}
