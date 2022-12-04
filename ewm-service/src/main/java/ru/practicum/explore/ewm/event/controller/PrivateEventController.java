package ru.practicum.explore.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.event.dto.EventDtoInput;
import ru.practicum.explore.ewm.event.dto.EventDtoInputOnUpdate;
import ru.practicum.explore.ewm.event.dto.EventDtoOutput;
import ru.practicum.explore.ewm.event.service.EventService;
import ru.practicum.explore.ewm.request.dto.RequestDto;
import ru.practicum.explore.ewm.request.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    public EventDtoOutput create(@PathVariable Long userId,
                                 @Valid @RequestBody EventDtoInput eventDtoInput) {
        log.info("create new event userId={}, eventDtoInput={}", userId, eventDtoInput);
        return eventService.create(userId, eventDtoInput);
    }

    @PatchMapping
    public EventDtoOutput update(@PathVariable Long userId,
                                 @Valid @RequestBody EventDtoInputOnUpdate eventDtoInput) {

        log.info("update event userId={}, eventDtoInput={}", userId, eventDtoInput);
        return eventService.updateEventFromCreator(eventDtoInput, userId);
    }

    @GetMapping
    public List<EventDtoOutput> getAllByUserId(@PathVariable Long userId,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {

        log.info("get all event by userId,  userId={}, from={}, size={}", userId, from, size);
        return eventService.getAllByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventDtoOutput getById(@PathVariable Long userId,
                                  @PathVariable Long eventId) {

        log.info("get event by id, userId={}, eventId={}", userId, eventId);

        return eventService.getByIdAndUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDtoOutput cancellation(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("cancel event by id, userId={}, eventId={}", userId, eventId);
        return eventService.cancellation(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequestsByUser(@PathVariable Long userId,
                                              @PathVariable Long eventId) {

        log.info("get requests by user, userId={}, eventId={}", userId, eventId);
        return requestService.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmed(@PathVariable Long userId,
                                @PathVariable Long eventId,
                                @PathVariable Long reqId) {

        log.info("confirm someone else's request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return requestService.confirmed(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejected(@PathVariable Long userId,
                               @PathVariable Long eventId,
                               @PathVariable Long reqId) {

        log.info("reject someone else's request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return requestService.rejected(userId, eventId, reqId);
    }

}
