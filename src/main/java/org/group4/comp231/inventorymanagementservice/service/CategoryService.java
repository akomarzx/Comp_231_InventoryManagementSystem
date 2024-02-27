package org.group4.comp231.inventorymanagementservice.service;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.dto.category.UpdateCategoryDto;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.group4.comp231.inventorymanagementservice.mapper.category.CategoryMapper;
import org.group4.comp231.inventorymanagementservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends BaseService {

    private final CategoryRepository categoryRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, TenantIdentifierResolver tenantIdentifierResolver,
                            CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getCategories(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        return this.categoryRepository.findBy();
    }

    public void createCategory(CreateCategoryDto createUpdateCategoryDto, Long tenantId, String createdBy) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Category newCategory = this.categoryMapper.partialUpdate(createUpdateCategoryDto, new Category());
        newCategory.setTenant(tenantId);
        newCategory.setCreatedBy(createdBy);
        newCategory.setCreatedAt(Instant.now());
        this.categoryRepository.save(newCategory);
    }

    public CategoryDto updateCategory(Long categoryId, UpdateCategoryDto dto, Long tenantId, String updatedBy) {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Optional<Category> category = this.categoryRepository.findById(categoryId);

        if(category.isPresent()) {

            Category entity = this.categoryMapper.partialUpdate(dto, category.get());
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.categoryRepository.save(entity);
            return this.categoryMapper.toCategoryDto(entity);

        } else {
            return null;
        }
    }
}
