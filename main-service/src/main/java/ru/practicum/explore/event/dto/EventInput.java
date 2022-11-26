package ru.practicum.explore.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class EventInput {
    @NotNull(message = "title is null")
    @Size(min = 3, max = 120, message = "3 < title < 120")
    private String title;
    @JsonProperty("category")
    private Long categoryId;
    @NotNull(message = "annotation is null")
    @Size(min = 20, max = 2000, message = "20 < annotation < 2000")
    private String annotation;
    @NotNull(message = "description is null")
    @Size(min = 20, max = 7000, message = "20 < description < 7000")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private EventLocation eventLocation;
    private Long participantLimit;
    private Boolean isPaid;
    private Boolean requestModeration;
}
