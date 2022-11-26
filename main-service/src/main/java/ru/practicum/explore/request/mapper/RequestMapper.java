package ru.practicum.explore.request.mapper;

import ru.practicum.explore.request.dto.RequestDto;
import ru.practicum.explore.request.entity.RequestEntity;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static RequestEntity toRequestEntity(RequestDto requestDto) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setId(requestDto.getId());
        requestEntity.setRequestStatus(requestDto.getStatus());
        requestEntity.setRequesterId(requestDto.getRequesterId());
        requestEntity.setCreated(requestDto.getCreated());
        requestEntity.setEventId(requestDto.getEventId());
        return requestEntity;
    }

    public static RequestDto toRequestDto(RequestEntity requestEntity) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(requestEntity.getId());
        requestDto.setEventId(requestEntity.getEventId());
        requestDto.setCreated(requestEntity.getCreated());
        requestDto.setStatus(requestEntity.getRequestStatus());
        requestDto.setRequesterId(requestEntity.getRequesterId());
        return requestDto;
    }

    public static List<RequestDto> requestDtoList(List<RequestEntity> requestEntityList) {
        List<RequestDto> result = new ArrayList<>();

        for (RequestEntity requestEntity : requestEntityList) {
            result.add(toRequestDto(requestEntity));
        }
        return result;
    }
}
