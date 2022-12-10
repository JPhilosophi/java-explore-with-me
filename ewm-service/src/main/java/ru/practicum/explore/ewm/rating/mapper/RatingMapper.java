package ru.practicum.explore.ewm.rating.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.ewm.rating.dto.RatingDto;
import ru.practicum.explore.ewm.rating.dto.RatingInputDto;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingEntity toRatingEntity(RatingInputDto ratingInputDto);

    RatingDto toRatingDto(RatingEntity ratingEntity);
}
