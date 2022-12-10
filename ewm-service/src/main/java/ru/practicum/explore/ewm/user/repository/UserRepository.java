package ru.practicum.explore.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.ewm.user.dto.UserRating;
import ru.practicum.explore.ewm.user.entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByIdIn(List<Long> ids, Pageable pageable);

    @Query(nativeQuery = true, value = "select u.name, count(r.liked) as ratings\n" +
            "from users u\n" +
            "inner join events e on u.id = e.initiator_id\n" +
            "inner join ratings r on e.id = r.event_id\n" +
            "group by u.id\n" +
            "order by ratings DESC;")
    List<UserRating> getUsersWithRating();
}
