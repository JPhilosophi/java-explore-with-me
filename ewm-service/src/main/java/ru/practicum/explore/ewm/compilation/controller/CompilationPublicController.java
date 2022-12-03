package ru.practicum.explore.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.compilation.dto.CompilationDto;
import ru.practicum.explore.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@Valid
                         @RequestParam(name = "pinned", required = false, defaultValue = "true") Boolean pinned,
                         @PositiveOrZero @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                         @Positive @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        log.info("get compilations pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("get compilation by id={}", compId);
        return compilationService.getById(compId);
    }
}
