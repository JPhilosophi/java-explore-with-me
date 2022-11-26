package ru.practicum.explore.compilation.service;

import ru.practicum.explore.compilation.dto.CompilationInputDto;
import ru.practicum.explore.compilation.dto.CompilationOutputDto;

import java.util.List;

public interface CompilationService {
    CompilationOutputDto create(CompilationInputDto compilation);

    void delete(Long compId);

    void addEventInCompilation(Long compId, Long eventId);

    void delEventFromCompilation(Long compId, Long eventId);

    void changePin(Long compId, Boolean pinned);

    CompilationOutputDto getById(Long compId);

    List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size);
}
