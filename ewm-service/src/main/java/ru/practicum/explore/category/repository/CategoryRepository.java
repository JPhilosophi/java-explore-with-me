package ru.practicum.explore.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
