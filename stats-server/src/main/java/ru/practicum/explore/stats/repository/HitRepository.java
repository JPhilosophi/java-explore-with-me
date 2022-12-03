package ru.practicum.explore.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.stats.dto.Hit;
import ru.practicum.explore.stats.entity.HitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<HitEntity, Long> {

    @Query("select app, uri, count (ip) as hits from hits " +
            "where uri in :uris and (created >= :start and created <= :end)" +
            "group by uri, app")
    List<Hit> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);


    @Query("select app, uri, count (distinct ip) as hits from hits " +
            "where uri in :uris and (created >= :start and created <= :end)" +
            "group by uri, app")
    List<Hit> getUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}

