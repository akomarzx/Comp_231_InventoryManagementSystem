package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<UserRepresentation>> getAllUserByTenant(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.keycloakClientService.getAllUsersByTenant(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    // Create New Non Admin User
}