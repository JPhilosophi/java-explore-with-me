package ru.practicum.explore.ewm.request.mapper;

import jdk.jfr.EventFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore.ewm.request.dto.RequestDto;
import ru.practicum.explore.ewm.request.entity.RequestEntity;
import ru.practicum.explore.ewm.user.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventFactory.class, UserService.class})
public interface RequestMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "requesterId", source = "requester.id")
    RequestDto toRequestDto(RequestEntity request);

    @Mapping(target = "requester.id", source = "userId")
    @Mapping(target = "event.id", source = "eventId")
    @Mapping(target = "status", constant = "PENDING")
    RequestEntity toRequest(Long userId, Long eventId);

    List<RequestDto> toRequestDto(List<RequestEntity> requests);
}