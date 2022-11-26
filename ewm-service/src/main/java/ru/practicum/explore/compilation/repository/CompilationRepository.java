package ru.practicum.explore.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.compilation.entity.CompilationEntity;

import java.util.List;
import java.util.Set;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
    CompilationEntity findByEventIds(Set<Long> eventIds);

    List<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);

}
