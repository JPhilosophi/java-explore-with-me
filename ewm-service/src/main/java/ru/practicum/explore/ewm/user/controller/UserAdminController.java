package ru.practicum.explore.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.user.dto.UserDto;
import ru.practicum.explore.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;


    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("create user {}", userDto);
        return userService.create(userDto);
    }

    @GetMapping
    public List<UserDto> getAll(@RequestParam(required = false) List<Long> ids,
                                @RequestParam(defaultValue = "0") Integer from,
                                @RequestParam(defaultValue = "10") Integer size) {

        log.info("get users all, list ids={}, from={}, size={}", ids, from, size);
        return userService.getAll(ids, from, size);
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable Long userId) {
        log.info("delete user, id={}", userId);
        userService.deleteById(userId);
    }
}
