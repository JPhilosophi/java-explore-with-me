package ru.practicum.explore.ewm.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.ewm.rating.entity.RatingEntity;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    RatingEntity findByLiked(Long userId);

}
