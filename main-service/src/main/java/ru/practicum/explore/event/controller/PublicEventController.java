package ru.practicum.explore.event.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventOutput;
import ru.practicum.explore.event.dto.EventShortOutput;
import ru.practicum.explore.event.dto.EventSort;
import ru.practicum.explore.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
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


    @GetMapping("/{id}")
    public EventOutput getById(@PathVariable Long id, HttpServletRequest request) {

        log.info("get event by id={}, requestURI={}, remoteAddr={}", id, request.getRequestURI(), request.getRemoteAddr());
       // statsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventService.findById(id);
    }

    @GetMapping
    public List<EventShortOutput> getEvents(HttpServletRequest request,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) @DateTimeFormat(
                                                       iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(
                                                       iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                       LocalDateTime rangeEnd,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) EventSort sort,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) String text) {

        log.info("get short public info about all events, categories={}, rangeStart={}," +
                        "rangeEnd={}, from={}, size={}, sort={}, onlyAvailable={}, paid={}, text={}",
                categories, rangeStart, rangeEnd, from, size, sort, onlyAvailable, paid, text);

        return eventService.getEventByParameters(null, null, categories, rangeStart, rangeEnd,
                        from, size, sort, onlyAvailable, paid, text);
    }
}
