package ru.practicum.explore.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore.ewm.rating.service.RatingService;
import ru.practicum.explore.ewm.user.dto.UserRatingDto;
import ru.practicum.explore.ewm.user.dto.UserShortDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PublicUserController {
    private final RatingService ratingService;

    @GetMapping()
    public List<UserRatingDto> rating() {
        return ratingService.userRatings();
    }
}
