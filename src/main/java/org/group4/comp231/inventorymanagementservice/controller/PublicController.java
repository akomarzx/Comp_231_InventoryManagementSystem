package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Tag(name = "Public API", description = "All Public APIs available")
public class PublicController {

    private final TenantService tenantService;

    public PublicController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/tenant")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Registration Endpoint for new business to register into the platform.")
    public ResponseEntity<ObjectUtils.Null> createTenant(@Valid @RequestBody UserRegistrationDto dto) throws Exception {
        this.tenantService.createTenant(dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
