package ru.practicum.explore.event.service;

import ru.practicum.explore.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventOutput create(EventInput event, Long userId);

    EventOutput getById(Long userId, Long eventId);

    EventOutput publish(Long eventId);

    EventOutput reject(Long eventId);

    EventOutput updateByAdmin(EventInput event, Long eventId);

    EventOutput updateByCreator(EventUpdate event, Long userId);

    List<EventShortOutput> getEventByParameters(List<Long> users, List<EventState> eventStates,
                                                List<Long> categories,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                Integer from, Integer size, EventSort eventSort, Boolean onlyAvailable,
                                                Boolean paid, String text);

    EventOutput findById(Long id);

    EventOutput cancellation(Long userId, Long eventId);

    List<EventOutput> getAll(Long userId, Integer from, Integer size);
}
