package ru.practicum.explore.compilation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CompilationInputDto {
    @NotBlank(message = "title is blank")
    private String title;
    private Boolean isPinned;
    private Set<Long> events;
}
