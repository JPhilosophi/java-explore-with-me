package ru.practicum.explore.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.entity.HitEntity;
import ru.practicum.explore.stats.mapper.HitMapper;
import ru.practicum.explore.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl {
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    public void create(HitDto hitDto) {
        HitEntity hitEntity = hitMapper.toHit(hitDto);
        hitRepository.save(hitEntity);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                 List<String> uris, Boolean unique) {
        if (unique) {
            return hitMapper.toStats(hitRepository.getUniqueHits(start, end, uris));
        }
        return hitMapper.toStats(hitRepository.getHits(start, end, uris));
    }
}
