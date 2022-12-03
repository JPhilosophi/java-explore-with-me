package ru.practicum.explore.ewm.statistics.service;

public interface StatisticsService {
    void setHits(String uri, String ip);

    Long getHits(Long id);
}
