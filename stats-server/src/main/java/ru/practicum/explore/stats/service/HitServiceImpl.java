package ru.practicum.explore.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.stats.dto.Hit;
import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.mapper.HitMapper;
import ru.practicum.explore.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService{
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    public void create(HitDto hitDto) {
        hitRepository.save(hitMapper.toHit(hitDto));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<Hit> entities;
        if (unique) {
            entities = hitRepository.getUniqueHits(start, end, uris);
            return hitMapper.toListHit(entities);
        }
        entities = hitRepository.getHits(start, end, uris);
        return hitMapper.toListHit(entities);
    }
}
