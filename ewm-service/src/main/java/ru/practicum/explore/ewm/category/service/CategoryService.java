package ru.practicum.explore.ewm.category.service;

import ru.practicum.explore.ewm.category.dto.CategoryDto;
import ru.practicum.explore.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto category);

    CategoryDto update(CategoryDto category);

    void delete(Long catId);

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long catId);
}
