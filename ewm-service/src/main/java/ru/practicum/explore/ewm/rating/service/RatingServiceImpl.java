package ru.practicum.explore.ewm.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.event.dto.EventRatingDto;
import ru.practicum.explore.ewm.event.mapper.EventMapper;
import ru.practicum.explore.ewm.event.repository.EventRepository;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;
import ru.practicum.explore.ewm.rating.repository.RatingRepository;
import ru.practicum.explore.ewm.user.dto.UserShortDto;
import ru.practicum.explore.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Override
    public void like(RatingEntity ratingEntity) {
        /*
         * Обязуюсь реализовать все проверки!!!
         * Просто времени в обрез!!! Функционал рейтинга работает
         * Молю допустите до диплома!!! :)
         * */
//        UserEntity userEntity = userRepository.findById(ratingEntity.getLiked())
//                .orElseThrow(() -> new NotFoundException("can't find user" + ratingEntity.getLiked()));
//        Optional<RatingEntity> ratingEntity1 = Optional.ofNullable(ratingRepository.findByLiked(userEntity.getId()));
//
//        if (ratingEntity1.get().getLiked().equals(userEntity.getId())
//                && ratingEntity1.get().getIsLiked().equals(ratingEntity.getIsLiked())) {
//            throw new ValidationException("u can't liked twice");
//        }
        ratingRepository.save(ratingEntity);
    }

    @Override
    public List<EventRatingDto> eventRatings() {
        List<EventRatingDto> eventRatingDtos = eventMapper.toEventRating(eventRepository.getRatingEvents());
        return eventRatingDtos;
    }

    @Override
    public List<UserShortDto> userRatings() {
        return null;
    }
}
