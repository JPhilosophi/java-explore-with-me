package ru.practicum.explore.ewm.exeption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionDto {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
