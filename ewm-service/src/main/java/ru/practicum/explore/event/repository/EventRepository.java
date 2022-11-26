package ru.practicum.explore.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.event.entity.EventEntity;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    List<EventEntity> findAllByIdIn(List<Long> eventIds);

    Set<EventEntity> findByIdIn(Set<Long> eventIds);

    @Query(nativeQuery = true, value = "select ce.* from compilations_events as ce where ce.event_id  = ?")
    Set<Long> findAllById(Long eventId);
}
