package ru.practicum.explore.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.stats.dto.HitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.service.HitServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final HitServiceImpl hitService;

    @PostMapping("/hit")
    public void create(@RequestBody HitDto hitDto, HttpServletRequest request) {
        hitService.create(hitDto);
        log.info("Запрос к эндпоинту '{}' на добавление статистики {}",
                request.getRequestURI(), hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> get(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                    pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                    pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique,
            HttpServletRequest request) {
        log.info("Запрос к эндпоинту '{}' на получеие статистики статистики {}",
                request.getRequestURI(), String.join(", ", uris));

        return hitService.getStats(start, end, uris, unique);

    }
}
