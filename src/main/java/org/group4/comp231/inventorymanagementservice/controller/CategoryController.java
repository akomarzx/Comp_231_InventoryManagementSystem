package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.GetCategoriesDto;
import org.group4.comp231.inventorymanagementservice.services.CategoryService;
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
    public ResponseEntity<List<GetCategoriesDto>> getAllCategories(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.categoryService.getCategories(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new Category")
    public ResponseEntity<ObjectUtils.Null> createCategory(@RequestBody CreateCategoryDto createCategoryDto,
                                                           @AuthenticationPrincipal(expression = "claims['email']") String createdBy,
                                                           @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        this.categoryService.createCategory(createCategoryDto, Long.parseLong(tenantId), createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
