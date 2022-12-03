package ru.practicum.explore.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.request.dto.Status;
import ru.practicum.explore.ewm.request.entity.RequestEntity;
import ru.practicum.explore.ewm.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    Optional<RequestEntity> findByRequesterAndEvent(UserEntity requester, EventEntity event);

    Long countByEventAndStatus(EventEntity event, Status status);

    List<RequestEntity> findAllByRequester(UserEntity requester);

    List<RequestEntity> findAllByEvent(EventEntity event);
}

