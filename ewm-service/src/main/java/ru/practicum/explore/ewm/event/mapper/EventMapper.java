package ru.practicum.explore.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.explore.ewm.category.service.CategoryFactory;
import ru.practicum.explore.ewm.event.dto.EventDtoInput;
import ru.practicum.explore.ewm.event.dto.EventDtoInputOnUpdate;
import ru.practicum.explore.ewm.event.dto.EventDtoOutput;
import ru.practicum.explore.ewm.event.dto.EventDtoOutputShort;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.user.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryFactory.class, UserService.class})
public interface EventMapper {
    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "initiator.id", source = "userId")
    @Mapping(target = "lat", source = "eventDtoInput.location.lat")
    @Mapping(target = "lon", source = "eventDtoInput.location.lon")
    EventEntity toEventEntity (EventDtoInput eventDtoInput, Long userId);

    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "lat", source = "eventDtoInput.location.lat")
    @Mapping(target = "lon", source = "eventDtoInput.location.lon")
    @Mapping(target = "paid", source = "isPaid")
    EventEntity toEventEntity (EventDtoInput eventDtoInput);

    @Mapping(target = "category", source = "eventDtoInput.categoryId")
    @Mapping(target = "paid", source = "isPaid")
    EventEntity toEventEntity (EventDtoInputOnUpdate eventDtoInput);

    @Mapping(target = "createdOn", source = "created")
    @Mapping(target = "publishedOn", source = "published")
    @Mapping(target = "location.lat", source = "lat")
    @Mapping(target = "location.lon", source = "lon")
    EventDtoOutput toEventDtoOutput(EventEntity event);

    List<EventDtoOutput> toEventDtoOutput(List<EventEntity> events);

    List<EventDtoOutputShort> toDtoShort(List<EventEntity> events);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEvent(EventEntity newEvent, @MappingTarget EventEntity oldEvent);
}