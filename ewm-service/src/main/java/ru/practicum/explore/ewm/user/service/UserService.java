package ru.practicum.explore.ewm.user.service;

import ru.practicum.explore.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto user);

    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    void deleteById(Long userId);

    UserDto getById(Long userId);
}
