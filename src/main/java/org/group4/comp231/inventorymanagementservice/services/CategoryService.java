package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateUpdateCategoryDto;
import org.group4.comp231.inventorymanagementservice.mapper.GetCategoryMapper;
import org.group4.comp231.inventorymanagementservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final GetCategoryMapper getCategoryMapper;

    public CategoryService(CategoryRepository categoryRepository, TenantIdentifierResolver tenantIdentifierResolver,
                           GetCategoryMapper getCategoryMapper) {
        this.categoryRepository = categoryRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.getCategoryMapper = getCategoryMapper;
    }

    public List<CategoryDto> getCategories(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(getCategoryMapper::toDto).collect(Collectors.toList());
    }

    public void createCategory(CreateUpdateCategoryDto createUpdateCategoryDto, Long tenantId, String createdBy) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Category newCategory = new Category();
        newCategory.setTenant(tenantId);
        newCategory.setCreatedBy(createdBy);
        newCategory.setCreatedAt(Instant.now());
        newCategory.setLabel(createUpdateCategoryDto.label());
        newCategory.setDescription(createUpdateCategoryDto.description() != null ? createUpdateCategoryDto.description() : null);
        this.categoryRepository.save(newCategory);
    }

    public CategoryDto updateCategory(Long categoryId ,CreateUpdateCategoryDto dto, Long tenantId, String updatedBy) {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Optional<Category> category = this.categoryRepository.findById(categoryId);

        if(category.isPresent()) {

            Category entity = category.get();
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());
            entity.setLabel(dto.label());

            if (dto.description() != null) {
                entity.setDescription(dto.description());
            }

            this.categoryRepository.save(entity);
            return this.getCategoryMapper.toDto(entity);
        } else {
            return null;
        }
    }
}
