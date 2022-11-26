package ru.practicum.explore.statistics.service;

public interface StatisticsService {

    Long getHits(Long id);

    void setHits(String uri, String ip);

}
