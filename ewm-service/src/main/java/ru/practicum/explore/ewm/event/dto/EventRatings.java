package ru.practicum.explore.ewm.event.dto;

import java.time.LocalDateTime;

public interface EventRatings {
    Long getId();

    String getTitle();

    String getAnnotation();

    LocalDateTime getEventDate();

    Boolean getPaid();

    Long getRatings();
}
