package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.category.CategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.CreateCategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.category.UpdateCategoryDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserUpdateDto;
import org.group4.comp231.inventorymanagementservice.services.AccountService;
import org.group4.comp231.inventorymanagementservice.services.CategoryService;
import org.group4.comp231.inventorymanagementservice.services.KeycloakClientService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Account", description = "Endpoints for Managing Vendor and Seller Account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(description = "Get All Accounts")
    public ResponseEntity<List<CategoryDto>> getAllAccount(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.categoryService.getCategories(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new Seller/Vendor Account")
    public ResponseEntity<ObjectUtils.Null> createAccount(@Valid @RequestBody CreateCategoryDto createUpdateCategoryDto,
                                                           @AuthenticationPrincipal(expression = "claims['email']") String createdBy,
                                                           @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        this.categoryService.createCategory(createUpdateCategoryDto, Long.parseLong(tenantId), createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update Existing Vendor/Seller Information")
    public ResponseEntity<CategoryDto> updateAccount(@Valid @RequestBody UpdateCategoryDto updateCategoryDto,
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

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a Vendor/Seller Account")
    public ResponseEntity<ObjectUtils.Null> deleteAccount(@NotNull @PathVariable("id") String accountId) {
        this.keycloakClientService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}