package ru.practicum.explore.category.service;

import ru.practicum.explore.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto category);

    CategoryDto update(CategoryDto category);

    void delete(Long catId);

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long catId);
}
