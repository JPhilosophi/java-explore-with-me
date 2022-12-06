package ru.practicum.explore.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.ewm.category.dto.CategoryDto;
import ru.practicum.explore.ewm.category.dto.NewCategoryDto;
import ru.practicum.explore.ewm.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("create category {}", newCategoryDto);
        return categoryService.create(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("update category {}", categoryDto);
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("delete category, id={}", catId);
        categoryService.delete(catId);
    }

}
