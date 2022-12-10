package ru.practicum.explore.ewm.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.event.dto.EventRatingDto;
import ru.practicum.explore.ewm.event.mapper.EventRatingMapper;
import ru.practicum.explore.ewm.event.repository.EventRepository;
import ru.practicum.explore.ewm.exeption.NotFoundException;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;
import ru.practicum.explore.ewm.rating.repository.RatingRepository;
import ru.practicum.explore.ewm.user.dto.UserRatingDto;
import ru.practicum.explore.ewm.user.dto.UserShortDto;
import ru.practicum.explore.ewm.user.entity.UserEntity;
import ru.practicum.explore.ewm.user.mapper.UserMapper;
import ru.practicum.explore.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventRatingMapper eventRatingMapper;
    private final UserMapper userMapper;

    @Override
    public void like(RatingEntity ratingEntity) {
        userRepository.findById(ratingEntity.getLiked())
                .orElseThrow(() -> new NotFoundException("can't find user" + ratingEntity.getLiked()));
        eventRepository.findById(ratingEntity.getEventId())
                .orElseThrow(() -> new NotFoundException("can't find event" + ratingEntity.getEventId()));
        Optional<RatingEntity> rating = Optional.ofNullable(ratingRepository.findByLiked(ratingEntity.getLiked()));
        rating.ifPresent(entity -> ratingRepository.deleteById(entity.getId()));
        ratingRepository.save(ratingEntity);
    }

    @Override
    public List<EventRatingDto> eventRatings() {
        List<EventRatingDto> eventRatingDtos =eventRatingMapper.toEventRating(eventRepository.getAll());
        for (EventRatingDto eventRatingDto : eventRatingDtos) {
            eventRatingDto.setEventDate(eventRepository.findById(eventRatingDto.getId()).get().getEventDate());
        }
        return eventRatingDtos;
    }

    @Override
    public List<UserRatingDto> userRatings() {
        List<UserRatingDto> userRatingDtos = userMapper.toUserRating(userRepository.getUsersWithRating());
        return userRatingDtos;
    }

}
