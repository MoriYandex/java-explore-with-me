package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationsService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationsService compilationsService;

    @GetMapping
    public List<CompilationDto> get(@RequestParam(required = false, defaultValue = "false") Boolean pinned,
                                    @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        return compilationsService.get(pinned, PageRequest.of(from, size));
    }

    @GetMapping("/{id}")
    public CompilationDto getById(@PathVariable Long id) {
        return compilationsService.getById(id);
    }
}
