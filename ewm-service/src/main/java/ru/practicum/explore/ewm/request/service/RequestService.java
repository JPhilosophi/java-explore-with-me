package ru.practicum.explore.ewm.request.service;

import ru.practicum.explore.ewm.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto create(Long userId, Long eventId);

    List<RequestDto> getRequestsByUser(Long userId);

    List<RequestDto> getRequests(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);

    RequestDto confirmed(Long userId, Long eventId, Long reqId);

    RequestDto rejected(Long userId, Long eventId, Long reqId);
}
