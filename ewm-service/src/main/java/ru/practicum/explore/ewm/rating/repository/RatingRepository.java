package ru.practicum.explore.ewm.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    RatingEntity findByLiked(Long userId);
}
