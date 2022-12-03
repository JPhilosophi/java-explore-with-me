package ru.practicum.explore.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.ewm.category.dto.CategoryDto;
import ru.practicum.explore.ewm.category.dto.NewCategoryDto;
import ru.practicum.explore.ewm.category.entity.CategoryEntity;
import ru.practicum.explore.ewm.category.mapper.CategoryMapper;
import ru.practicum.explore.ewm.category.repository.CategoryRepository;
import ru.practicum.explore.ewm.exeption.ConflictException;
import ru.practicum.explore.ewm.exeption.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(NewCategoryDto category) {
        try {
            CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
            categoryEntity = categoryRepository.save(categoryEntity);
            return categoryMapper.toCategoryDto(categoryEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category name already in use", "name = " + category.getName());
        }
    }

    @Override
    public CategoryDto update(CategoryDto category) {
        CategoryEntity oldCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new NotFoundException("can't find"));
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setId(oldCategory.getId());
        newCategory.setName(category.getName());
        categoryRepository.delete(oldCategory);
        try {
            categoryRepository.save(newCategory);
            return  categoryMapper.toCategoryDto(newCategory);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category name already in use", "name = " + category.getName());
        }
    }

    @Override
    public void delete(Long catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("category cannot be deleted as it is used in events", "catId = " + catId);
        }
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable).getContent();
        return categoryMapper.toDto(categoryEntities);
    }

    @Override
    public CategoryDto getById(Long catId) {
        CategoryEntity category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.toCategoryDto(category);
    }
}
