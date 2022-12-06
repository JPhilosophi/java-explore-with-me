package ru.practicum.explore.ewm.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleValidationException(ValidationException ex) {
        return ExceptionDto.builder()
                .errors(new ArrayList<>())
                .message(ex.getMessage())
                .reason("")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleConflictException(ConflictException ex) {
        return ExceptionDto.builder()
                .errors(new ArrayList<>())
                .message(ex.getMessage())
                .reason(ex.getReason())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNotFoundException(NotFoundException ex) {
        return ExceptionDto.builder()
                .errors(new ArrayList<>())
                .message(ex.getMessage())
                .reason("")
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleIsBlankException(MethodArgumentNotValidException ex) {
        return ExceptionDto.builder()
                .errors(new ArrayList<>())
                .message(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .reason("")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

