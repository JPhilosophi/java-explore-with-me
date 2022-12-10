package ru.practicum.explore.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingDto {
    private String name;
    private Long ratings;
}
