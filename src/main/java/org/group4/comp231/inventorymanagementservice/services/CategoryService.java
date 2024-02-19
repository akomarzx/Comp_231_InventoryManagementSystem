package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.category.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.GetCategoriesDto;
import org.group4.comp231.inventorymanagementservice.mapper.GetCategoryMapper;
import org.group4.comp231.inventorymanagementservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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

    public List<GetCategoriesDto> getCategories(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(getCategoryMapper::toDto).collect(Collectors.toList());
    }

    public void createCategory(CreateCategoryDto createCategoryDto, Long tenantId, String createdBy) {
        Category newCategory = new Category();
        newCategory.setTenant(tenantId);
        newCategory.setCreatedBy(createdBy);
        newCategory.setCreatedAt(Instant.now());
        newCategory.setLabel(createCategoryDto.label());
        newCategory.setDescription(createCategoryDto.description() != null ? createCategoryDto.description() : null);
        this.categoryRepository.save(newCategory);
    }
}
