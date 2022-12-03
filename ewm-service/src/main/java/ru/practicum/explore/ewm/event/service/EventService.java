package ru.practicum.explore.ewm.event.service;

import ru.practicum.explore.ewm.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDtoOutput create(Long userId, EventDtoInput event);

    EventDtoOutput updateEventFromAdmin(EventDtoInput event, Long eventId);

    EventDtoOutput updateEventFromCreator(EventDtoInputOnUpdate event, Long userId);

    EventDtoOutput publish(Long eventId);

    EventDtoOutput reject(Long eventId);

    EventDtoOutput cancellation(Long userId, Long eventId);

    EventDtoOutput findById(Long id);

    EventDtoOutput getByIdAndUserId(Long userId, Long eventId);

    List<EventDtoOutput> getEventByParameters(List<Long> users, List<State> states,
                                              List<Long> categories,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              Integer from, Integer size, Sort sort, Boolean onlyAvailable,
                                              Boolean paid, String text);

    List<EventDtoOutput> getAllByUserId(Long userId, Integer from, Integer size);
}
