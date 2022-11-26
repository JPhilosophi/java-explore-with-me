package ru.practicum.explore.compilation.mapper;

import ru.practicum.explore.compilation.dto.CompilationInputDto;
import ru.practicum.explore.compilation.dto.CompilationOutputDto;
import ru.practicum.explore.compilation.entity.CompilationEntity;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {
    public static CompilationEntity toCompilationEntity(CompilationInputDto compilationInputDto) {
        CompilationEntity compilation = new CompilationEntity();
        compilation.setTitle(compilationInputDto.getTitle());
        compilation.setPinned(compilationInputDto.getIsPinned());
        return compilation;
    }


    public static CompilationOutputDto toCompilationOutputDto(CompilationEntity compilationEntity) {
        CompilationOutputDto compilationOutputDto = new CompilationOutputDto();
        compilationOutputDto.setId(compilationEntity.getId());
        compilationOutputDto.setTitle(compilationEntity.getTitle());
        compilationOutputDto.setIsPinned(compilationEntity.getPinned());
        return compilationOutputDto;
    }

    public static List<CompilationOutputDto> toCompilationOutputDtoList(List<CompilationEntity> compilationEntitys) {
        List<CompilationOutputDto> result = new ArrayList<>();

        for (CompilationEntity CompilationEntity : compilationEntitys) {
            result.add(toCompilationOutputDto(CompilationEntity));
        }
        return result;
    }
}
