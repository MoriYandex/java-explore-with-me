package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationDto;
import ru.practicum.main.compilation.service.CompilationsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationsService compilationsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationsService.create(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        compilationsService.delete(id);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable Long id,
                                 @RequestBody @Valid UpdateCompilationDto updateCompilationDto) {
        return compilationsService.update(id, updateCompilationDto);
    }
}
