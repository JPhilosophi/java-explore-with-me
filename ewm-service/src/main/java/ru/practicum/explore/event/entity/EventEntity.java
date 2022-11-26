package ru.practicum.explore.event.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.event.dto.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "category_id")
    private Long categoryId;
    @JoinColumn(name = "initiator_id")
    private Long initiatorId;
    private String title;
    @Column(length = 1000)
    private String annotation;
    @Column(length = 1000)
    private String description;
    @CreationTimestamp
    private LocalDateTime created;
    private LocalDateTime published;
    private LocalDateTime eventDate;
    private Float lat;
    private Float lon;
    private Boolean isPaid;
    private Long participantLimit; // 100
    private Boolean requestModeration;
    @Enumerated(value = EnumType.STRING)
    private EventState eventState;
    private Long requestsId;
    @Transient
    private Long viewsId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        EventEntity event = (EventEntity) obj;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
