package ru.practicum.explore.request.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.request.dto.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    private Long requesterId;
    @CreationTimestamp
    private LocalDateTime created;
    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestEntity requestEntity = (RequestEntity) o;
        return Objects.equals(id, requestEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
