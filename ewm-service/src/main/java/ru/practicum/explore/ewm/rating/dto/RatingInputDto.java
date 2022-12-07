package ru.practicum.explore.ewm.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingInputDto {
    private Long eventId;
    private Long liked;
    private Boolean isLiked;
}
