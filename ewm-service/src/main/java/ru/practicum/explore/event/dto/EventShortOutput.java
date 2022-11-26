package ru.practicum.explore.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.user.dto.ShortUser;

import java.time.LocalDateTime;

@Data
public class EventShortOutput {
    private Long id;
    private String title;
    private CategoryDto categoryDto;
    private ShortUser initiator;
    private String annotation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Boolean isPaid;
    private Long views;
    private Long confirmedRequests;
}
