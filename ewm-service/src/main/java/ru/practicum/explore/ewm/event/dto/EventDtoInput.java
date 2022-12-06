package ru.practicum.explore.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoInput {
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
    private Location location;
    private Long participantLimit;
    @JsonProperty("paid")
    private Boolean isPaid;
    private Boolean requestModeration;
}
