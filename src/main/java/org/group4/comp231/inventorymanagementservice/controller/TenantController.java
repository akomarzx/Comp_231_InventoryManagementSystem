package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.tenant.CreateUpdateTenantDTO;
import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDTO;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Tenant", description = "Endpoints for Tenant Management")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/tenant")
    @Tag(name = "Get All Tenants", description = "View all current Tenant in the Platform. Available only for superusers")
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        List<TenantDTO> list = this.tenantService.getAllTenants();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/public/tenant")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Tag(name = "New Tenant Endpoint", description = "Public Endpoint for creating new Tenant in the SaaS platform")
    public ResponseEntity<ObjectUtils.Null> createTenant(@Valid @RequestBody UserRegistrationDto dto) throws Exception {
        this.tenantService.createTenant(dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PatchMapping("tenant/{id}")
    @Tag(name = "New Tenant Endpoint", description = "Public Endpoint for creating new Tenant in the SaaS platform")
    public ResponseEntity<TenantDTO> updateTenant(@PathVariable("id") Long id ,
                                                  @Valid @RequestBody CreateUpdateTenantDTO createUpdateTenantDTO,
                                                  @AuthenticationPrincipal(expression = "claims['email']") String username){

        TenantDTO updateTenantDto = this.tenantService.updateTenant(id, createUpdateTenantDTO, username);

        if (updateTenantDto != null) {
            return new ResponseEntity<>(updateTenantDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
