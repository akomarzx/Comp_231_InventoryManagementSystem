package org.group4.comp231.inventorymanagementservice.mapper.category;

import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.UpdateCategoryDto;
import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CreateCategoryDto createCategoryDto);

    CreateCategoryDto toCreateCategoryDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CreateCategoryDto createCategoryDto, @MappingTarget Category category);

    Category toEntity(UpdateCategoryDto updateCategoryDto);

    UpdateCategoryDto toUpdateCategoryDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(UpdateCategoryDto updateCategoryDto, @MappingTarget Category category);

    Category toEntity(CategoryDto updateCategoryDto);

    CategoryDto toCategoryDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);
}