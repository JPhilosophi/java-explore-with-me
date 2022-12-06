package ru.practicum.explore.stats.service;

import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {

    void create(HitDto hitDto);

    List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end,
                           List<String> uris, Boolean unique);
}
