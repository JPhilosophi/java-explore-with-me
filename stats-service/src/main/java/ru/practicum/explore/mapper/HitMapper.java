package ru.practicum.explore.mapper;

import ru.practicum.explore.dto.EndpointHitDto;
import ru.practicum.explore.dto.ViewStatsDto;
import ru.practicum.explore.entity.EndpointHitEntity;

import java.util.ArrayList;
import java.util.List;

public class HitMapper {
    public static EndpointHitEntity toEndpointHitEntity(EndpointHitDto endpointHitDto) {
        EndpointHitEntity endpointHitEntity = new EndpointHitEntity();
        endpointHitEntity.setId(endpointHitDto.getId());
        endpointHitEntity.setUri(endpointHitDto.getUri());
        endpointHitEntity.setIp(endpointHitDto.getIp());
        endpointHitEntity.setCreated(endpointHitDto.getCreated());
        endpointHitEntity.setApp(endpointHitDto.getApp());
        return endpointHitEntity;
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHitEntity endpointHitEntity) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setId(endpointHitEntity.getId());
        endpointHitDto.setUri(endpointHitEntity.getUri());
        endpointHitDto.setIp(endpointHitEntity.getIp());
        endpointHitDto.setCreated(endpointHitEntity.getCreated());
        endpointHitDto.setApp(endpointHitEntity.getApp());
        return endpointHitDto;
    }

    public static List<EndpointHitDto> toEndpointHitDtos(List<EndpointHitEntity> endpointHitEntities) {
        List<EndpointHitDto> result = new ArrayList<>();
        for (EndpointHitEntity endpointHitEntity : endpointHitEntities) {
            result.add(toEndpointHitDto(endpointHitEntity));
        }
        return result;
    }

    public static ViewStatsDto toViewStatsDto(EndpointHitDto endpointHitDto) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setUri(endpointHitDto.getUri());
        viewStatsDto.setApp(endpointHitDto.getApp());
        viewStatsDto.setHits(endpointHitDto.getId());
        return viewStatsDto;
    }

    public static List<ViewStatsDto> toStats(List<EndpointHitDto> hitsDto) {
        List<ViewStatsDto> result = new ArrayList<>();
        for (EndpointHitDto endpointHitDto : hitsDto) {
            result.add(toViewStatsDto(endpointHitDto));
        }
        return result;
    }
}
