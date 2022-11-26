package ru.practicum.explore.compilation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "compilations")
public class CompilationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
    //private Set<Long> eventIds = new HashSet<>();

//    public void addEventId(Long eventId){
//
//        this.getEventIds().add(eventId);
//    }
}
