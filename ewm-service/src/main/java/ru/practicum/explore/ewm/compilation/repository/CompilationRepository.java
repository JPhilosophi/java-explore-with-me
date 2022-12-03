package ru.practicum.explore.ewm.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.ewm.compilation.entity.CompilationEntity;

import java.util.List;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
    List<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);
}
