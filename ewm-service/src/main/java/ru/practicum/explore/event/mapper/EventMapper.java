package ru.practicum.explore.event.mapper;

import ru.practicum.explore.event.dto.EventInput;
import ru.practicum.explore.event.dto.EventOutput;
import ru.practicum.explore.event.dto.EventShortOutput;
import ru.practicum.explore.event.dto.EventUpdate;
import ru.practicum.explore.event.entity.EventEntity;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static EventEntity toEventEntity(EventInput eventInput, Long userId) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(eventInput.getTitle());
        eventEntity.setCategoryId(eventInput.getCategoryId());
        eventEntity.setAnnotation(eventInput.getAnnotation());
        eventEntity.setDescription(eventInput.getDescription());
        eventEntity.setEventDate(eventInput.getEventDate());
        eventEntity.setLat(eventInput.getEventLocation().getLat());
        eventEntity.setLon(eventInput.getEventLocation().getLon());
        eventEntity.setParticipantLimit(eventInput.getParticipantLimit());
        eventEntity.setIsPaid(eventInput.getIsPaid());
        eventEntity.setRequestModeration(eventInput.getRequestModeration());
        eventEntity.setInitiatorId(userId);
        return eventEntity;
    }

    public static EventEntity toEventEntityUpdate(EventUpdate eventUpdate) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(eventUpdate.getId());
        eventEntity.setTitle(eventUpdate.getTitle());
        eventEntity.setCategoryId(eventUpdate.getCategoryId());
        eventEntity.setAnnotation(eventUpdate.getAnnotation());
        eventEntity.setDescription(eventUpdate.getDescription());
        eventEntity.setEventDate(eventUpdate.getEventDate());
        eventEntity.setIsPaid(eventUpdate.getIsPaid());
        eventEntity.setParticipantLimit(eventUpdate.getParticipantLimit());
        return eventEntity;
    }

    public static EventOutput toEventOutput(EventEntity event) {
        EventOutput eventOutput = new EventOutput();
        eventOutput.setId(event.getId());
        eventOutput.setTitle(event.getTitle());
        eventOutput.setAnnotation(event.getAnnotation());
        eventOutput.setEventDate(event.getEventDate());
        eventOutput.setIsPaid(event.getIsPaid());
        eventOutput.setViews(event.getViewsId());
        return eventOutput;
    }

    public static EventShortOutput toEventShortOutput(EventEntity event) {
        EventShortOutput eventShortOutput = new EventShortOutput();
        eventShortOutput.setId(event.getId());
        eventShortOutput.setEventDate(event.getEventDate());
        eventShortOutput.setAnnotation(event.getAnnotation());
        eventShortOutput.setViews(event.getViewsId());
        eventShortOutput.setTitle(event.getTitle());
        eventShortOutput.setIsPaid(event.getIsPaid());
        return eventShortOutput;
    }

    public static List<EventOutput> toEventOutputList(List<EventEntity> eventEntityList) {
        List<EventOutput> result = new ArrayList<>();

        for (EventEntity eventEntity : eventEntityList) {
            result.add(toEventOutput(eventEntity));
        }
        return result;
    }

    public static List<EventShortOutput> toEventShortOutputList(List<EventEntity> eventEntityList) {
        List<EventShortOutput> result = new ArrayList<>();

        for (EventEntity eventEntity : eventEntityList) {
            result.add(toEventShortOutput(eventEntity));
        }
        return result;
    }

}
