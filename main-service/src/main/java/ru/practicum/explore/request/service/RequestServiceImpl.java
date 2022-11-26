package ru.practicum.explore.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.dto.EventState;
import ru.practicum.explore.event.entity.EventEntity;
import ru.practicum.explore.event.repository.EventRepository;
import ru.practicum.explore.exception.ConflictException;
import ru.practicum.explore.exception.NotFoundException;
import ru.practicum.explore.request.dto.RequestDto;
import ru.practicum.explore.request.dto.RequestStatus;
import ru.practicum.explore.request.entity.RequestEntity;
import ru.practicum.explore.request.mapper.RequestMapper;
import ru.practicum.explore.request.repository.RequestRepository;
import ru.practicum.explore.user.entity.UserEntity;
import ru.practicum.explore.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public RequestDto create(RequestDto request) {
        EventEntity eventEntity = eventRepository
                .findById(request.getEventId()).orElseThrow(() -> new NotFoundException("can't finde event"));
        userRepository
                .findById(request.getRequesterId()).orElseThrow(() -> new NotFoundException("user not found"));
        if (!eventEntity.getEventState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event must be published", "event state = " + eventEntity.getEventState());
        }
        if (request.getRequesterId().equals(eventEntity.getRequestsId())) {
            throw new ConflictException("Initiator can not be requester", "user id = " + request.getRequesterId());
        }
        requestRepository.findByRequesterIdAndEventId(request.getRequesterId(), request.getEventId())
                .ifPresent((r) -> {
                    throw new ConflictException("Request already exist", "Request id = " + request.getId());
                });
        Long requestCount = requestRepository.countByEventIdAndRequestStatus(request.getEventId(), RequestStatus.CONFIRMED);
        if (requestCount >= eventEntity.getParticipantLimit()) {
            throw new ConflictException("Event participant limit exceeded", "request count" + requestCount);
        }
        if (!eventEntity.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        RequestEntity requestEntity = requestRepository.save(RequestMapper.toRequestEntity(request));
        return RequestMapper.toRequestDto(requestEntity);
    }

    @Override
    public List<RequestDto> getRequestsByUser(Long userId) {
        UserEntity userEntity = userRepository
                .findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        List<RequestEntity> requestEntities = requestRepository.findAllByRequesterId(userEntity.getId());
        return RequestMapper.requestDtoList(requestEntities);
    }

    @Override
    public List<RequestDto> getRequests(Long userId, Long eventId) {
        EventEntity event = eventRepository
                .findById(eventId).orElseThrow(() -> new NotFoundException("event not found"));
        if (event.getInitiatorId().equals(userId)) {
            List<RequestEntity> requestEntities = requestRepository.findAllByEventId(event.getId());
            return RequestMapper.requestDtoList(requestEntities);
        }
        throw new ValidationException("only the initiator can view info " +
                "about requests to participate in the event");
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("request not found"));
        if (request.getRequesterId().equals(userId)) {
            request.setRequestStatus(RequestStatus.CANCELED);
            request = requestRepository.save(request);
            return RequestMapper.toRequestDto(request);
        }
        throw new ConflictException("request can only be canceled by its creator", "");
    }

    @Override
    public RequestDto confirmed(Long userId, Long eventId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("request not found"));
        EventEntity event = eventRepository
                .findById(eventId).orElseThrow(() -> new NotFoundException("event not found"));
        if (event.getId().equals(eventId) && event.getInitiatorId().equals(userId) &&
            event.getRequestModeration() && event.getParticipantLimit() > 0) {
            request.setRequestStatus(RequestStatus.CONFIRMED);
            request = requestRepository.save(request);
            return RequestMapper.toRequestDto(request);
        }
        throw new ConflictException("the request has the right to confirm the event creator", "");
    }

    @Override
    public RequestDto rejected(Long userId, Long eventId, Long requestId) {
        RequestEntity request = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("request not found"));
        EventEntity event = eventRepository
                .findById(eventId).orElseThrow(() -> new NotFoundException("event not found"));

        if (event.getId().equals(eventId) && event.getInitiatorId().equals(userId)) {
            request.setRequestStatus(RequestStatus.REJECTED);
            request = requestRepository.save(request);
            return RequestMapper.toRequestDto(request);
        }
        throw new ConflictException("the request has the right to reject the event creator", "");
    }
}
