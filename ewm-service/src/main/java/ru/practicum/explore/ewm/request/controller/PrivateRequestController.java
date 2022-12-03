package ru.practicum.explore.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.request.dto.RequestDto;
import ru.practicum.explore.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto create(@PathVariable Long userId,
                             @RequestParam("eventId") Long eventId) {
        log.info("Creating participation request userId={}, eventId={}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @GetMapping
    public List<RequestDto> getRequestsByUser(@PathVariable Long userId) {
        log.info("User requests for participation in other people's events, userId={}", userId);
        return requestService.getRequestsByUser(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info("Cancel your event request userId={}, requestId={}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
