package ru.practicum.explore.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.event.dto.EventDtoOutput;
import ru.practicum.explore.ewm.event.dto.EventRatingDto;
import ru.practicum.explore.ewm.event.dto.Sort;
import ru.practicum.explore.ewm.event.service.EventService;
import ru.practicum.explore.ewm.rating.dto.RatingInputDto;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;
import ru.practicum.explore.ewm.rating.mapper.RatingMapper;
import ru.practicum.explore.ewm.rating.service.RatingService;
import ru.practicum.explore.ewm.statistics.service.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    private final StatisticsService statisticsService;
    private final RatingService ratingService;
    private final RatingMapper ratingMapper;

    @GetMapping("/{id}")
    public EventDtoOutput getById(@PathVariable Long id, HttpServletRequest request) {

        log.info("get event by id={}, requestURI={}, remoteAddr={}", id, request.getRequestURI(), request.getRemoteAddr());
        statisticsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventService.findById(id);
    }

    @GetMapping
    public List<EventDtoOutput> getEvents(HttpServletRequest request,
                                          @RequestParam(required = false) List<Long> categories,
                                          @RequestParam(required = false) @DateTimeFormat(
                                                  iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                  LocalDateTime rangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(
                                                  iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                  LocalDateTime rangeEnd,
                                          @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                          @Positive @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(required = false) Sort sort,
                                          @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                          @RequestParam(required = false) Boolean paid,
                                          @RequestParam(required = false) String text) {

        log.info(request.getRequestURI() + " get short public info about all events, categories={}, rangeStart={}," +
                        "rangeEnd={}, from={}, size={}, sort={}, onlyAvailable={}, paid={}, text={}",
                categories, rangeStart, rangeEnd, from, size, sort, onlyAvailable, paid, text);
        statisticsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventByParameters(null, null, categories, rangeStart, rangeEnd,
                from, size, sort, onlyAvailable, paid, text);
    }

    @PostMapping("/like")
    public void like(@Valid @RequestBody RatingInputDto ratingInputDto){
        RatingEntity ratingEntity = ratingMapper.toRatingEntity(ratingInputDto);
        ratingService.like(ratingEntity);
    }

    @GetMapping("/like")
    public List<EventRatingDto> rating() {
        return ratingService.eventRatings();
    }
}
