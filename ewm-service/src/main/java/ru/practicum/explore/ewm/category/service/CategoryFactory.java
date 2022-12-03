package ru.practicum.explore.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.category.entity.CategoryEntity;
import ru.practicum.explore.ewm.category.repository.CategoryRepository;
import ru.practicum.explore.ewm.exeption.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryFactory {
    private final CategoryRepository categoryRepository;

    public CategoryEntity getById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
