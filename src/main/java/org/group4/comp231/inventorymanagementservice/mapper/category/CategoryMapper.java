package org.group4.comp231.inventorymanagementservice.mapper.category;

import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateUpdateCategoryDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CreateUpdateCategoryDto createUpdateCategoryDto);

    CreateUpdateCategoryDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CreateUpdateCategoryDto createUpdateCategoryDto, @MappingTarget Category category);
}