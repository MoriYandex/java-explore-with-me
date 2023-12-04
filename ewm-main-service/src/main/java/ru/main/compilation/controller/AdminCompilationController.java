package ru.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.main.compilation.dto.CompilationDto;
import ru.main.compilation.dto.NewCompilationDto;
import ru.main.compilation.dto.UpdateCompilationRequestDto;
import ru.main.compilation.service.CompilationsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final CompilationsService compilationsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto createCompilation(@RequestBody(required = false) @Valid NewCompilationDto newCompilationDto) {
        return compilationsService.createCompilation(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompilation(@PathVariable Long id) {
        compilationsService.deleteCompilation(id);
    }

    @PatchMapping("/{id}")
    public CompilationDto updateCompilation(@PathVariable Long id,
                                            @RequestBody @Valid UpdateCompilationRequestDto updateCompilationRequestDto) {
        return compilationsService.updateCompilation(id, updateCompilationRequestDto);
    }
}
