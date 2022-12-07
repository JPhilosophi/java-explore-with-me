package ru.practicum.explore.ewm.rating.service;

import ru.practicum.explore.ewm.event.dto.EventRatingDto;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;
import ru.practicum.explore.ewm.user.dto.UserShortDto;

import java.util.List;

public interface RatingService {

    void like(RatingEntity ratingEntity);

    List<EventRatingDto> eventRatings();

    List<UserShortDto> userRatings();


}
