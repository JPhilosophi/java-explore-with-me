package ru.practicum.explore.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.ewm.event.dto.EventRatings;
import ru.practicum.explore.ewm.event.entity.EventEntity;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query(nativeQuery = true, value = "select *\n" +
            "from (select e.id, e.title,e.annotation, e.event_date, e.paid, (select count(*) from ratings r where r.event_id = e.id) as ratings\n" +
            "from events e) as result\n" +
            "order by result.ratings desc;")
    List<EventRatings> getRatingEvents();
}
