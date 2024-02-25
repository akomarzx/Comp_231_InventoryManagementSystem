package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.category.UpdateCategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.group4.comp231.inventorymanagementservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Product Category", description = "Endpoints for managing product category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(description = "Get All Categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.categoryService.getCategories(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new Category")
    public ResponseEntity<ObjectUtils.Null> createCategory(@Valid @RequestBody CreateCategoryDto createUpdateCategoryDto,
                                                           @AuthenticationPrincipal(expression = "claims['email']") String createdBy,
                                                           @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        this.categoryService.createCategory(createUpdateCategoryDto, Long.parseLong(tenantId), createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update existing category")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto,
                                                      @NotNull @PathVariable("id") Long id,
                                                      @AuthenticationPrincipal(expression = "claims['email']") String updatedBy,
                                                      @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {

        CategoryDto categoryDto = this.categoryService.updateCategory(id, updateCategoryDto, Long.parseLong(tenantId), updatedBy);

        if (categoryDto != null) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
