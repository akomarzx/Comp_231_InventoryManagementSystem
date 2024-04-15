package org.group4.comp231.inventorymanagementservice.service;

import org.group4.comp231.inventorymanagementservice.dto.category.CategorySummary;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Category;
import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
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

    public List<CategorySummary> getCategories() {
        return this.categoryRepository.findAllBy();
    }

    /**
     * Create new Product Category
     * @param createUpdateCategoryDto
     * @param createdBy
     */
    public void createCategory(CategoryDto createUpdateCategoryDto, String createdBy) {
        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Category newCategory = this.categoryMapper.partialUpdate(createUpdateCategoryDto, new Category());
        newCategory.setTenant(tenantId);
        newCategory.setCreatedBy(createdBy);
        newCategory.setCreatedAt(Instant.now());
        this.categoryRepository.save(newCategory);
    }

    /**
     * Update Product Category
     * @param categoryId
     * @param dto
     * @param updatedBy
     * @throws Exception When Entity was not found
     */
    public void updateCategory(Long categoryId, CategoryDto dto, String updatedBy) throws Exception {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        Optional<Category> category = this.categoryRepository.findByTenantAndId(tenantId, categoryId);

        if(category.isPresent()) {

            Category entity = this.categoryMapper.partialUpdate(dto, category.get());
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.categoryRepository.save(entity);
        } else {
            throw new Exception("Entity Not Found");
        }
    }

    /**
     * Delete Category By ID
     * @param categoryId
     */
    public void deleteCategory(Long categoryId) {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        Optional<Category> toBeDeleted = this.categoryRepository.findByTenantAndId(tenantId, categoryId);

        if(toBeDeleted.isEmpty()) {
            return;
        }

        this.categoryRepository.deleteById(categoryId);
    }

}
