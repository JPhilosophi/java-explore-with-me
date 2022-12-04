package ru.practicum.explore.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.event.dto.*;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.event.mapper.EventMapper;
import ru.practicum.explore.ewm.event.repository.EventRepository;
import ru.practicum.explore.ewm.exeption.NotFoundException;
import ru.practicum.explore.ewm.request.dto.Status;
import ru.practicum.explore.ewm.statistics.service.StatisticsService;
import ru.practicum.explore.ewm.user.entity.UserEntity;
import ru.practicum.explore.ewm.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final int MIN_HOURS_FROM_CREATE_TO_EVENT = 2;
    private static final int MIN_HOURS_FROM_PUBLISHED_TO_EVENT = 1;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final EntityManager entityManager;
    private final StatisticsService statisticsService;

    @Override
    public EventDtoOutput create(Long userId, EventDtoInput event) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_CREATE_TO_EVENT);

        if (timeLimit.isBefore(event.getEventDate())) {
            EventEntity eventEntity = eventMapper.toEventEntity(event);
            eventEntity.setState(State.PENDING);
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("can't find"));
            eventEntity.setInitiator(userEntity);
            eventEntity = eventRepository.save(eventEntity);

            return eventMapper.toEventDtoOutput(eventEntity);
        }
        throw new ValidationException("wrong event date");
    }

    @Override
    public EventDtoOutput updateEventFromAdmin(EventDtoInput event, Long eventId) {
        EventEntity oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));
        EventEntity newEvent = eventMapper.toEventEntity(event);
        eventMapper.updateEvent(newEvent, oldEvent);
        oldEvent = eventRepository.save(oldEvent);

        return eventMapper.toEventDtoOutput(oldEvent);
    }

    @Override
    public EventDtoOutput updateEventFromCreator(EventDtoInputOnUpdate event, Long userId) {
        EventEntity newEvent = eventMapper.toEventEntity(event);
        EventEntity oldEvent = eventRepository.findById(newEvent.getId()).orElseThrow(() -> new NotFoundException("can't find"));

        if (oldEvent.getInitiator().getId().equals(userId)) {
            eventMapper.updateEvent(newEvent, oldEvent);
            oldEvent = eventRepository.save(oldEvent);
            return eventMapper.toEventDtoOutput(oldEvent);
        }
        throw new ValidationException("wrong user id = " + userId);
    }

    @Override
    public EventDtoOutput publish(Long eventId) {
        LocalDateTime timeLimit = LocalDateTime.now().plusHours(MIN_HOURS_FROM_PUBLISHED_TO_EVENT);
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));

        if (event.getState().equals(State.PENDING) && timeLimit.isBefore(event.getEventDate())) {
            event.setState(State.PUBLISHED);
            event.setPublished(LocalDateTime.now());
            event = eventRepository.save(event);
            return eventMapper.toEventDtoOutput(event);
        }
        throw new ValidationException("wrong state or wrong event date");
    }

    @Override
    public EventDtoOutput reject(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));

        if (event.getState().equals(State.PENDING)) {
            event.setState(State.CANCELED);
            event = eventRepository.save(event);
            return eventMapper.toEventDtoOutput(event);
        }
        throw new ValidationException("wrong state");
    }

    @Override
    public EventDtoOutput cancellation(Long userId, Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));

        if (event.getInitiator().getId().equals(userId) && event.getState().equals(State.PENDING)) {
            event.setState(State.CANCELED);
            event = eventRepository.save(event);
            return eventMapper.toEventDtoOutput(event);
        }
        throw new ValidationException("wrong state or wrong user id");
    }

    @Override
    public EventDtoOutput findById(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));

        if (event.getState().equals(State.PUBLISHED)) {
            setViews(event);
            return eventMapper.toEventDtoOutput(event);
        }
        throw new ValidationException("wrong state or wrong event id");
    }

    @Override
    public EventDtoOutput getByIdAndUserId(Long userId, Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("can't find"));
        if (event.getInitiator().getId().equals(userId)) {
            setViews(event);
            return eventMapper.toEventDtoOutput(event);
        }
        throw new NotFoundException("user Id not equal initiator Id");
    }

    @Override
    public List<EventDtoOutput> getEventByParameters(List<Long> users, List<State> states, List<Long> categories,
                                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                                     Integer size, Sort sort, Boolean onlyAvailable, Boolean paid, String text) {
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

        if (sort == Sort.EVENT_DATE) {
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
            return eventMapper.toEventDtoOutput(events.stream()
                    .filter(event -> {
                        long count = event.getRequests().stream()
                                .filter(request -> request.getStatus().equals(Status.CONFIRMED))
                                .count();
                        return event.getParticipantLimit() > count;
                    })
                    .collect(Collectors.toList()));
        }
        return eventMapper.toEventDtoOutput(events);
    }

    @Override
    public List<EventDtoOutput> getAllByUserId(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<EventEntity> eventEntities = eventRepository.findAllByInitiatorId(userId, pageable);
        return eventMapper.toEventDtoOutput(eventEntities);
    }

    private void setViews(EventEntity event) {
        Long hits = statisticsService.getHits(event.getId());
        event.setViews(hits);
    }
}