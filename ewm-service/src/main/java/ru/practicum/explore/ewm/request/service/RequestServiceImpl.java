package ru.practicum.explore.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.event.dto.State;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.event.repository.EventRepository;
import ru.practicum.explore.ewm.exeption.ConflictException;
import ru.practicum.explore.ewm.exeption.NotFoundException;
import ru.practicum.explore.ewm.exeption.ValidationException;
import ru.practicum.explore.ewm.request.dto.RequestDto;
import ru.practicum.explore.ewm.request.dto.Status;
import ru.practicum.explore.ewm.request.entity.RequestEntity;
import ru.practicum.explore.ewm.request.mapper.RequestMapper;
import ru.practicum.explore.ewm.request.repository.RequestRepository;
import ru.practicum.explore.ewm.user.entity.UserEntity;
import ru.practicum.explore.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    public RequestDto create(Long userId, Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        RequestEntity requestEntity = requestMapper.toRequest(userId, eventId);
        if (!eventEntity.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event must be published", "event state = " + eventEntity.getState());
        }
        if (userEntity.equals(eventEntity.getInitiator())) {
            throw new ConflictException("Initiator can not be requester", "user id = " + userEntity.getId());
        }

        this.requestRepository.findByRequesterAndEvent(userEntity, eventEntity)
                .ifPresent((r) -> {
                    throw new ConflictException("Request already exist", "Request id = " + r.getId());
                });
        Long requestCount = this.requestRepository.countByEventAndStatus(eventEntity, Status.CONFIRMED);
        if (requestCount >= eventEntity.getParticipantLimit()) {
            throw new ConflictException("Event participant limit exceeded", "request count" + requestCount);
        }
        if (!eventEntity.getRequestModeration()) {
            requestEntity.setStatus(Status.CONFIRMED);
        }
        requestEntity = requestRepository.save(requestEntity);
        return requestMapper.toRequestDto(requestEntity);

    }

    @Override
    public List<RequestDto> getRequestsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        List<RequestEntity> requestEntities =
                requestRepository.findAllByRequester(user);
        return requestMapper.toRequestDto(requestEntities);
    }

    @Override
    public List<RequestDto> getRequests(Long userId, Long eventId) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow();
        if (event.getInitiator().getId().equals(userId)) {
            List<RequestEntity> participationRequestEntities
                    = requestRepository.findAllByEvent(event);
            return requestMapper.toRequestDto(participationRequestEntities);
        }
        throw new ValidationException("only the initiator can view info " +
                "about requests to participate in the event");
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("can't find"));

        if (request.getRequester().getId().equals(userId)) {
            request.setStatus(Status.CANCELED);
            request = requestRepository.save(request);
            return requestMapper.toRequestDto(request);
        }
        throw new ConflictException("request can only be canceled by its creator", "");
    }

    @Override
    public RequestDto confirmed(Long userId, Long eventId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("can't find"));
        EventEntity event = request.getEvent();

        if (event.getId().equals(eventId) && event.getInitiator().getId().equals(userId) &&
                event.getRequestModeration() && event.getParticipantLimit() > 0) {
            request.setStatus(Status.CONFIRMED);
            request = requestRepository.save(request);
            return requestMapper.toRequestDto(request);
        }
        throw new ConflictException("the request has the right to confirm the event creator", "");
    }

    @Override
    public RequestDto rejected(Long userId, Long eventId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("can't find"));
        EventEntity event = request.getEvent();

        if (event.getId().equals(eventId) && event.getInitiator().getId().equals(userId)) {
            request.setStatus(Status.REJECTED);
            request = requestRepository.save(request);
            return requestMapper.toRequestDto(request);
        }
        throw new ConflictException("the request has the right to reject the event creator", "");
    }
}
