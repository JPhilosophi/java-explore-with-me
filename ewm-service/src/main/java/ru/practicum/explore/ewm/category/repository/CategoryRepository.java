package ru.practicum.explore.ewm.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.ewm.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
