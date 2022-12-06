package ru.practicum.explore.ewm.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.ewm.category.entity.CategoryEntity;
import ru.practicum.explore.ewm.event.dto.State;
import ru.practicum.explore.ewm.request.entity.RequestEntity;
import ru.practicum.explore.ewm.user.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "initiator_id")
    private UserEntity initiator;
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
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private Set<RequestEntity> requests;
    @Transient
    private Long views;
}
