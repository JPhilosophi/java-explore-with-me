package ru.practicum.explore.ewm.category.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.explore.ewm.category.dto.CategoryDto;
import ru.practicum.explore.ewm.category.dto.NewCategoryDto;
import ru.practicum.explore.ewm.category.entity.CategoryEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toCategoryEntity(NewCategoryDto categoryDto);

    CategoryDto toCategoryDto(CategoryEntity category);

    CategoryEntity toCategoryEntity(CategoryDto categoryDto);

    List<CategoryDto> toDto(List<CategoryEntity> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CategoryEntity newCategory, @MappingTarget CategoryEntity oldCategory);
}
