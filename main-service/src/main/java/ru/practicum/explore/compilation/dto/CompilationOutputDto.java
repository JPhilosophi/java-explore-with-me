package ru.practicum.explore.compilation.dto;

import lombok.Data;
import ru.practicum.explore.event.dto.EventShortOutput;

import java.util.List;

@Data
public class CompilationOutputDto {
    private Long id;
    private String title;
    private Boolean isPinned;
    private List<EventShortOutput> events;
}
