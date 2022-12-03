package ru.practicum.explore.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.compilation.dto.CompilationDto;
import ru.practicum.explore.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.explore.ewm.compilation.entity.CompilationEntity;
import ru.practicum.explore.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.explore.ewm.compilation.repository.CompilationRepository;
import ru.practicum.explore.ewm.event.entity.EventEntity;
import ru.practicum.explore.ewm.event.service.EventFactory;
import ru.practicum.explore.ewm.exeption.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService{
    private final CompilationRepository compilationRepository;
    private final EventFactory eventFactory;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto create(NewCompilationDto compilation) {
        CompilationEntity compilationEntity = compilationMapper.toCompilationEntity(compilation);
        compilationEntity = compilationRepository.save(compilationEntity);

        return compilationMapper.toCompilationDto(compilationEntity);
    }

    @Override
    public void delete(Long compilationId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("can't find"));
        compilationRepository.delete(compilationEntity);
    }

    @Override
    public void addEventInCompilation(Long compilationId, Long eventId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("can't find"));

        Set<EventEntity> events = compilationEntity.getEvents();
        events.add(eventFactory.getById(eventId));
        compilationEntity.setEvents(events);
        compilationRepository.save(compilationEntity);
    }

    @Override
    public void delEventFromCompilation(Long compilationId, Long eventId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("can't find"));
        Set<EventEntity> events = compilationEntity.getEvents();
        events.remove(eventFactory.getById(eventId));
        compilationEntity.setEvents(events);
        compilationRepository.save(compilationEntity);
    }

    @Override
    public void changePin(Long compilationId, Boolean pinned) {
        CompilationEntity compilationEntity = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("can't find"));
        compilationEntity.setPinned(pinned);
        compilationRepository.save(compilationEntity);
    }

    @Override
    public CompilationDto getById(Long compilationId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("can't find"));
        return compilationMapper.toCompilationDto(compilationEntity);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<CompilationEntity> compilationEntities =  compilationRepository.findAllByPinned(pinned, pageable);
        return compilationMapper.toDto(compilationEntities);
    }
}
