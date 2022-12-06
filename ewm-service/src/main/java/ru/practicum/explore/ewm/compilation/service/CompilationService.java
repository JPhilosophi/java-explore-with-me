package ru.practicum.explore.ewm.compilation.service;

import ru.practicum.explore.ewm.compilation.dto.CompilationDto;
import ru.practicum.explore.ewm.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto compilation);

    void delete(Long compId);

    void addEventInCompilation(Long compId, Long eventId);

    void delEventFromCompilation(Long compId, Long eventId);

    void changePin(Long compId, Boolean pinned);

    CompilationDto getById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);
}
