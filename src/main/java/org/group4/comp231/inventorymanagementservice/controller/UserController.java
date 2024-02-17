package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserUpdateDto;
import org.group4.comp231.inventorymanagementservice.services.KeycloakClientService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "User Management", description = "Endpoints for User Management")
public class UserController {

    private final KeycloakClientService keycloakClientService;

    public UserController(KeycloakClientService service) {
        this.keycloakClientService = service;
    }

    @GetMapping
    @Operation(description = "Get all users by under a company.")
    public ResponseEntity<List<UserRepresentation>> getAllUserByTenant(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.keycloakClientService.getAllUsersByTenant(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(description = "Create new user under an existing company")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ObjectUtils.Null> addNewUserForTenant(@Valid @RequestBody UserRegistrationDto userRegistrationDto,
                                                                @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        this.keycloakClientService.registerNewUser(userRegistrationDto, Long.parseLong(tenantId), false);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update User Information")
    public ResponseEntity<ObjectUtils.Null> updateUserForTenant(@NotNull @PathVariable("id") String userId,
                                                                @Valid @RequestBody UserUpdateDto userUpdateDto,
                                                                @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {

        this.keycloakClientService.updateUser(userId, userUpdateDto, Long.parseLong(tenantId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete User")
    public ResponseEntity<ObjectUtils.Null> deleteUserForTenant(@NotNull @PathVariable("id") String userId) {

        this.keycloakClientService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}