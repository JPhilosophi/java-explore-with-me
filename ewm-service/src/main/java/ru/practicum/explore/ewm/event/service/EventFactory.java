package ru.practicum.explore.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.event.repository.EventRepository;
import ru.practicum.explore.ewm.exeption.NotFoundException;

@Service
@RequiredArgsConstructor
public class EventFactory {
    private final EventRepository eventRepository;

    public EventEntity getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }
}
