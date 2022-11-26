package ru.practicum.explore.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.request.dto.RequestStatus;
import ru.practicum.explore.request.entity.RequestEntity;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    Optional<RequestEntity> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    Long countByEventIdAndRequestStatus(Long eventId, RequestStatus status);

    List<RequestEntity> findAllByRequesterId(Long requester);

    List<RequestEntity> findAllByEventId(Long event);
}
