package ru.practicum.explore.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.user.dto.User;
import ru.practicum.explore.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("create user {}", user);
        return userService.create(user);
    }

    @GetMapping
    public List<User> getAll(@RequestParam(required = false) List<Long> ids,
                             @RequestParam(defaultValue = "0") Integer from,
                             @RequestParam(defaultValue = "10") Integer size) {
        log.info("get users all, list ids={}, from={}, size={}", ids, from, size);
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("delete user, id={}", userId);
        userService.deleteUser(userId);
    }
}
