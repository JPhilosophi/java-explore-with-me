package ru.practicum.explore.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore.ewm.event.dto.EventRatingDto;
import ru.practicum.explore.ewm.event.dto.EventRatings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventRatingMapper {

    @Mapping(target = "eventDate", source = "eventRatings.getEventDate()")
    List<EventRatingDto> toEventRating(List<EventRatings> eventRatings);

}