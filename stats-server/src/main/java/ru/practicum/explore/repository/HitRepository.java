package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHitEntity, Long> {

    @Query("select app, uri, count (ip) as hits from EndpointHitEntity " +
            "where uri in :uris and (created >= :start and created <= :end)" +
            "group by uri, app")
    List<EndpointHitEntity> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select app, uri, count (distinct ip) as hits from EndpointHitEntity " +
            "where uri in :uris and (created >= :start and created <= :end)" +
            "group by uri, app")
    List<EndpointHitEntity> getUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
