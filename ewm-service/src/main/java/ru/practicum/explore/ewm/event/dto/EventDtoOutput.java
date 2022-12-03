package ru.practicum.explore.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.ewm.category.dto.CategoryDto;
import ru.practicum.explore.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoOutput {
    private Long id;
    private String title;
    private CategoryDto category;
    private UserShortDto initiator;
    private String annotation;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Long participantLimit;
    private Boolean paid;
    private Boolean requestModeration;
    private State state;
    private Long views;
    private Long confirmedRequests;
}
