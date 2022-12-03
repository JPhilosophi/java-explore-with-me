package ru.practicum.explore.stats.mapper;


import org.mapstruct.Mapper;
import ru.practicum.explore.stats.dto.Hit;
import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.entity.HitEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HitMapper {

    HitEntity toHit(HitDto hitDto);

    List<ViewStatsDto> toStats(List<Hit> hit);

}