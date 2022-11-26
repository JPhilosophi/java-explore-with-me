package ru.practicum.explore.service;

import ru.practicum.explore.dto.EndpointHitDto;
import ru.practicum.explore.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {

    void create(EndpointHitDto endpointHit);

    List<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end,
                                  List<String> uris, Boolean unique);

}
