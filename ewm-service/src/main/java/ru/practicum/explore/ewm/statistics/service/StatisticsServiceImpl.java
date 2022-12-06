package ru.practicum.explore.ewm.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.statistics.client.StatsClient;
import ru.practicum.explore.ewm.statistics.dto.HitDto;
import ru.practicum.explore.ewm.statistics.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private static final String APP_NAME = "ewm-service";
    private final StatsClient client;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setHits(String uri, String ip) {
        HitDto hitDto = new HitDto(null, APP_NAME, uri, ip, LocalDateTime.now());
        log.info("Отправляем запрос на сервер статистики для {}", uri);

        HitDto answer = client.setStat(hitDto);
        log.info("Получен ответ от сервера статистики {}", answer);
    }

    @Override
    public Long getHits(Long id) {
        List<ViewStatsDto> stats;
        try {
            stats = client.getStats(
                    LocalDateTime.of(2020, 1, 1, 0, 0).format(formatter),
                    LocalDateTime.now().format(formatter), new String[]{"/events/" + id}, false);
        } catch (Exception e) {
            return 0L;
        }
        if (stats == null || stats.isEmpty()) {
            return 0L;
        }
        return stats.get(0).getHits();
    }
}
