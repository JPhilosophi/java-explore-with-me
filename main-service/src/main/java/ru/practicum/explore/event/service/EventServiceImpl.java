package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.entity.EventEntity;
import ru.practicum.explore.event.mapper.EventMapper;
import ru.practicum.explore.event.repository.EventRepository;
import ru.practicum.explore.exception.NotFoundException;
import ru.practicum.explore.exception.ValidationException;
import ru.practicum.explore.statistics.service.StatisticsService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final int MIN_HOURS_FROM_CREATE_TO_EVENT = 2;
    private static final int MIN_HOURS_FROM_PUBLISHED_TO_EVENT = 1;

    private final EventRepository eventRepository;
    private final EntityManager entityManager;
    private final StatisticsService statisticsService;

    @Override
    public EventOutput create(EventInput event, Long userId) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_CREATE_TO_EVENT);
        if (timeLimit.isBefore(event.getEventDate())) {
            EventEntity eventEntity = EventMapper.toEventEntity(event, userId);
            eventEntity.setEventState(EventState.PENDING);
            eventEntity = eventRepository.save(eventEntity);
            return EventMapper.toEventOutput(eventEntity);
        }
        throw new ValidationException("wrong event date");
    }

    @Override
    public EventOutput getById(Long userId, Long eventId) {
        EventEntity eventEntity = eventRepository
                .findById(eventId).orElseThrow(() -> new NotFoundException("not found event"));
        if (eventEntity.getInitiatorId().equals(userId)) {
            setViews(eventEntity);
            return EventMapper.toEventOutput(eventEntity);
        }
        throw new NotFoundException("user Id not equal initiator Id");
    }

    @Override
    public EventOutput publish(Long eventId) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_PUBLISHED_TO_EVENT);
        EventEntity eventEntity = getById(eventId);

        if (eventEntity.getEventState().equals(EventState.PENDING) && timeLimit.isBefore(eventEntity.getEventDate())) {
            eventEntity.setEventState(EventState.PUBLISHED);
            eventEntity.setPublished(LocalDateTime.now());
            eventEntity = eventRepository.save(eventEntity);
            return EventMapper.toEventOutput(eventEntity);
        }
        throw new ValidationException("wrong state or wrong event date");
    }

    @Override
    public EventOutput reject(Long eventId) {
        EventEntity eventEntity = getById(eventId);

        if (eventEntity.getEventState().equals(EventState.PENDING)) {
            eventEntity.setEventState(EventState.CANCELED);
            eventEntity = eventRepository.save(eventEntity);
            return EventMapper.toEventOutput(eventEntity);
        }
        throw new ValidationException("wrong state");
    }

    private EventEntity getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Override
    public EventOutput updateByAdmin(EventInput event, Long eventId) {
        EventEntity eventEntity = getById(eventId);
        eventEntity = EventMapper.toEventEntity(event, eventEntity.getInitiatorId());
        eventEntity = eventRepository.save(eventEntity);
        return EventMapper.toEventOutput(eventEntity);
    }

    @Override
    public EventOutput updateByCreator(EventUpdate event, Long userId) {
        EventEntity eventEntity = getById(event.getId());
        if (eventEntity.getInitiatorId().equals(userId)) {
            eventEntity = EventMapper.toEventEntityUpdate(event);
            eventEntity = eventRepository.save(eventEntity);
            return EventMapper.toEventOutput(eventEntity);
        }
        throw new ValidationException("wrong user id = " + userId);
    }

    @Override
    public List<EventShortOutput> getEventByParameters(List<Long> users, List<EventState> states, List<Long> categories,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  Integer from, Integer size,
                                                  EventSort sort, Boolean onlyAvailable, Boolean paid, String text) {

        List<Predicate> predicates = new ArrayList<>();
        List<EventEntity> events;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventEntity> criteria = cb.createQuery(EventEntity.class);
        Root<EventEntity> eventRoot = criteria.from(EventEntity.class);

        if (users != null && !users.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("initiator").get("id")).value(users));
        }
        if (states != null && !states.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("state")).value(states));
        }
        if (categories != null && !categories.isEmpty()) {
            predicates.add(cb.in(eventRoot.get("category").get("id")).value(categories));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThan(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(cb.lessThan(eventRoot.get("eventDate"), rangeEnd));
        }
        if (text != null) {
            predicates.add(cb.or(
                    cb.like(cb.upper(eventRoot.get("annotation")), "%" + text.toUpperCase() + "%"),
                    cb.like(cb.upper(eventRoot.get("description")), "%" + text.toUpperCase() + "%")
            ));
        }
        if (paid != null) {
            predicates.add(cb.equal(eventRoot.get("paid"), paid));
        }

        if (sort == EventSort.EVENT_DATE) {
            events = entityManager.createQuery(criteria.select(eventRoot)
                            .where(predicates.toArray(new Predicate[]{}))
                            .orderBy(cb.asc(eventRoot.get("eventDate"))))
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        } else {
            events = entityManager.createQuery(criteria.select(eventRoot)
                            .where(predicates.toArray(new Predicate[]{})))
                    .setFirstResult(from)
                    .setMaxResults(size)
                    .getResultList();
        }

        if (onlyAvailable != null && onlyAvailable) {
            events.stream().filter(eventEntity -> {
                long count = eventEntity.getRequestsId();
                return eventEntity.getParticipantLimit() > count;
            });
        }
        return EventMapper.toEventShortOutputList(events);
    }

    @Override
    public EventOutput findById(Long id) {
        EventEntity event = getById(id);

        if (event.getEventState().equals(EventState.PUBLISHED)) {
            setViews(event);
            return EventMapper.toEventOutput(event);
        }
        throw new ValidationException("wrong state or wrong event id");
    }

    @Override
    public EventOutput cancellation(Long userId, Long eventId) {
        EventEntity event = getById(eventId);

        if (event.getInitiatorId().equals(userId) && event.getEventState().equals(EventState.PENDING)) {
            event.setEventState(EventState.CANCELED);
            event = eventRepository.save(event);
            return EventMapper.toEventOutput(event);
        }
        throw new ValidationException("wrong state or wrong user id");
    }

    @Override
    public List<EventOutput> getAll(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "eventDate"));
        List<EventEntity> eventEntityList = eventRepository.findAllByInitiatorId(userId, pageable);
        return EventMapper.toEventOutputList(eventEntityList);
    }

    private void setViews(EventEntity event) {
        Long hits = statisticsService.getHits(event.getId());
        event.setViewsId(hits);
    }
}
