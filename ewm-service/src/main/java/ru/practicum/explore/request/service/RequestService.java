package ru.practicum.explore.request.service;

import ru.practicum.explore.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto create(RequestDto request);

    List<RequestDto> getRequestsByUser(Long userId);

    List<RequestDto> getRequests(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);

    RequestDto confirmed(Long userId, Long eventId, Long reqId);

    RequestDto rejected(Long userId, Long eventId, Long reqId);
}
