package ru.practicum.explore.ewm.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Long id;
    private Long eventId;
    private Long liked;
}
