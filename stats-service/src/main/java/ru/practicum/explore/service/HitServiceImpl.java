package ru.practicum.explore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.EndpointHitDto;
import ru.practicum.explore.entity.EndpointHitEntity;
import ru.practicum.explore.mapper.HitMapper;
import ru.practicum.explore.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public void create(EndpointHitDto endpointHit) {
        EndpointHitEntity endpointHitEntity = HitMapper.toEndpointHitEntity(endpointHit);
        endpointHitEntity = hitRepository.save(endpointHitEntity);
    }

    @Override
    public List<EndpointHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<EndpointHitDto> list = new ArrayList<>();
        if (unique) {
             list = HitMapper.toEndpointHitDtos(hitRepository.getUniqueHits(start, end, uris));
            return list;
        }
        list =  HitMapper.toEndpointHitDtos(hitRepository.getHits(start, end, uris));
        return list;
    }
}
