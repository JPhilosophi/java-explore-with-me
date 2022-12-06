package ru.practicum.explore.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.entity.HitEntity;
import ru.practicum.explore.stats.mapper.HitMapper;
import ru.practicum.explore.stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    public void create(HitDto hitDto) {
        HitEntity hitEntity = hitMapper.toHit(hitDto);
        hitEntity.setIp(hitDto.getIp());
        log.info("link: " + hitEntity);
        hitRepository.save(hitEntity);
    }

    @Override
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end,
                                  List<String> uris, Boolean unique) {
        List<String> validUris = new ArrayList<>();
        for (String str : uris) {
            if (!"/events".equals(str)) {
                validUris.add(str);
            }
        }
        if (unique) {
            return hitMapper.toStats(hitRepository.getUnique(start, end, validUris));
        }
        if (!validUris.isEmpty()) {
            return hitMapper.toStats(hitRepository.get(start, end, validUris));
        }
        return hitMapper.toStats(hitRepository.getAll(start, end));
    }
}
