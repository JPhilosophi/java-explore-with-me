package ru.practicum.explore.category.mapper;

import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    CategoryEntity toCategoryEntity(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDto.getId());
        categoryEntity.setName(categoryDto.getName());
        return categoryEntity;
    }

    CategoryDto toCategoryDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setName(categoryEntity.getName());
        return categoryDto;
    }

    List<CategoryDto> toDto(List<CategoryEntity> categories) {
        List<CategoryDto> result = new ArrayList<>();

        for (CategoryEntity categoryEntity : categories) {
            result.add(toCategoryDto(categoryEntity));
        }
        return result;
    }

}
