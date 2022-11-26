package ru.practicum.explore.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventInput;
import ru.practicum.explore.event.dto.EventOutput;
import ru.practicum.explore.event.dto.EventShortOutput;
import ru.practicum.explore.event.dto.EventState;
import ru.practicum.explore.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("/{eventId}/publish")
    public EventOutput publish(@PathVariable Long eventId) {
        log.info("admin publishes event id={}", eventId);
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventOutput reject(@PathVariable Long eventId) {
        log.info("admin rejects event id={}", eventId);
        return eventService.reject(eventId);
    }

    @PutMapping("/{eventId}")
    public EventOutput update(@PathVariable Long eventId,
                              @Valid @RequestBody EventInput eventInput) {
        log.info("event change by admin, eventId={}, eventDtoInput={}", eventId, eventInput);
        return eventService.updateByAdmin(eventInput, eventId);
    }

    @GetMapping
    public List<EventShortOutput> searchEvent(@RequestParam(required = false) List<Long> users,
                                              @RequestParam(required = false) List<EventState> states,
                                              @RequestParam(required = false) List<Long> categories,
                                              @RequestParam(required = false) @DateTimeFormat(
                                                      iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                      LocalDateTime rangeStart,
                                              @RequestParam(required = false) @DateTimeFormat(
                                                      iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
                                                      LocalDateTime rangeEnd,
                                              @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                              @Positive @RequestParam(defaultValue = "10") Integer size) {

        log.info("admin get full information about all events, users={}, states={}, categories={}, rangeStart={}," +
                "rangeEnd={}, from={}, size={}", users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventByParameters(users, states, categories, rangeStart, rangeEnd, from, size,
                null, null, null, null);
    }
}
