package ru.practicum.explore.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.ewm.event.dto.EventRatings;
import ru.practicum.explore.ewm.event.entity.EventEntity;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query(nativeQuery = true, value = "select e.id, e.title, e.event_date, e.paid, count(r.liked) as ratings\n" +
            "from events e\n" +
            "left join ratings r on e.id = r.event_id\n" +
            "group by e.id\n" +
            "order by ratings DESC\n")
    List<EventRatings> getAll();
}
