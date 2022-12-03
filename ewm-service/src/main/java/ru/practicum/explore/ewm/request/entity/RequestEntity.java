package ru.practicum.explore.ewm.request.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.relational.core.mapping.Table;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.request.dto.Status;
import ru.practicum.explore.ewm.user.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "requests")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private UserEntity requester;
    @CreationTimestamp
    private LocalDateTime created;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
