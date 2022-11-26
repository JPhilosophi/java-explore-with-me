package ru.practicum.explore.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explore.compilation.dto.CompilationInputDto;
import ru.practicum.explore.compilation.dto.CompilationOutputDto;
import ru.practicum.explore.compilation.entity.CompilationEntity;
import ru.practicum.explore.compilation.mapper.CompilationMapper;
import ru.practicum.explore.compilation.repository.CompilationRepository;
import ru.practicum.explore.event.dto.EventShortOutput;
import ru.practicum.explore.event.entity.EventEntity;
import ru.practicum.explore.event.mapper.EventMapper;
import ru.practicum.explore.event.repository.EventRepository;
import ru.practicum.explore.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    CompilationRepository compilationRepository;
    EventRepository eventRepository;

    @Override
    public CompilationOutputDto create(CompilationInputDto compilation) {
        CompilationEntity compilationEntity = CompilationMapper.toCompilationEntity(compilation);
        compilationEntity = compilationRepository.save(compilationEntity);
        return CompilationMapper.toCompilationOutputDto(compilationEntity);
    }

    @Override
    public void delete(Long compId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compId).orElseThrow();
        compilationRepository.delete(compilationEntity);
    }

    @Override
    public void addEventInCompilation(Long compId, Long eventId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compId).orElseThrow();
        Set<Long> eventIds = eventRepository.findAllById(eventId);
        Set<EventEntity> eventEntitySet = eventRepository.findByIdIn(eventIds);
        List<EventEntity> eventEntities = eventEntitySet.stream().collect(Collectors.toList());
        CompilationOutputDto compilationOutputDto = CompilationMapper.toCompilationOutputDto(compilationEntity);
        List<EventShortOutput> eventShortOutputs = EventMapper.toEventShortOutputList(eventEntities);
        compilationOutputDto.setEvents(eventShortOutputs);
    }

    @Override
    public void delEventFromCompilation(Long compId, Long eventId) {
        CompilationEntity compilationEntity = compilationRepository.findById(compId).orElseThrow();
        CompilationOutputDto compilationOutputDto = CompilationMapper.toCompilationOutputDto(compilationEntity);
        Set<Long> eventIds = eventRepository.findAllById(eventId);
        Set<EventEntity> eventEntitySet = eventRepository.findByIdIn(eventIds);
        List<EventEntity> eventEntities = eventEntitySet.stream().collect(Collectors.toList());
        eventEntities.remove(eventId);
        List<EventShortOutput> eventShortOutputs = EventMapper.toEventShortOutputList(eventEntities);
        compilationOutputDto.setEvents(eventShortOutputs);
        EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow();
        eventRepository.delete(eventEntity);
    }

    @Override
    public void changePin(Long compId, Boolean pinned) {
        CompilationEntity compilation = compilationRepository.findById(compId).orElseThrow();
        compilation.setPinned(pinned);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationOutputDto getById(Long compId) {
        CompilationEntity compilation = compilationRepository
                .findById(compId).orElseThrow(() -> new NotFoundException("compilation not found id= " + compId));
        return CompilationMapper.toCompilationOutputDto(compilation);
    }

    @Override
    public List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        List<CompilationEntity> compilationEntities = compilationRepository.findAllByPinned(pinned, pageable);
        return CompilationMapper.toCompilationOutputDtoList(compilationEntities);
    }
}