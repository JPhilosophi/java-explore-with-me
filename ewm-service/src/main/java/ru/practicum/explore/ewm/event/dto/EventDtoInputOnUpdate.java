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
public class EventDtoInputOnUpdate {
    @NotNull(message = "id can not be null")
    @JsonProperty("eventId")
    private Long id;
    @Size(min = 3, max = 120, message = "3 < title < 120")
    private String title;
    @JsonProperty("category")
    private Long categoryId;
    @Size(min = 20, max = 2000, message = "20 < annotation < 2000")
    private String annotation;
    @Size(min = 20, max = 7000, message = "20 < description < 7000")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonProperty("paid")
    private Boolean isPaid;
    private Long participantLimit;

}
