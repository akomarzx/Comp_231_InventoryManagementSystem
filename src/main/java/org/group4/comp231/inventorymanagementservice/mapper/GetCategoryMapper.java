package org.group4.comp231.inventorymanagementservice.mapper;

import org.group4.comp231.inventorymanagementservice.dto.category.GetCategoriesDto;
import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GetCategoryMapper {
    Category toEntity(GetCategoriesDto getCategoriesDto);

    GetCategoriesDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(GetCategoriesDto getCategoriesDto, @MappingTarget Category category);
}