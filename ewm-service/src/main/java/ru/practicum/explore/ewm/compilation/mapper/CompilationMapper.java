package ru.practicum.explore.ewm.compilation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.ewm.compilation.dto.CompilationDto;
import ru.practicum.explore.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.explore.ewm.compilation.entity.CompilationEntity;
import ru.practicum.explore.ewm.event.service.EventFactory;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventFactory.class})
public interface CompilationMapper {
    CompilationEntity toCompilationEntity(NewCompilationDto newCompilationDto);

    CompilationDto toCompilationDto(CompilationEntity compilation);

    List<CompilationDto> toDto(List<CompilationEntity> compilation);
}
