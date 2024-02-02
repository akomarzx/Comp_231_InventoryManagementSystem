package org.group4.comp231.inventorymanagementservice.controller;

import jakarta.validation.Valid;
import org.group4.comp231.inventorymanagementservice.dto.tenant.CreateUpdateTenantDTO;
import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDTO;
import org.group4.comp231.inventorymanagementservice.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        List<TenantDTO> list = this.tenantService.getAllTenants();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(@Valid @RequestBody CreateUpdateTenantDTO createUpdateTenantDTO, @AuthenticationPrincipal(expression = "claims['email']") String username) {
        TenantDTO newTenant = this.tenantService.createTenant(createUpdateTenantDTO, username);
        return new ResponseEntity<>(newTenant, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TenantDTO> updateTenant(@PathVariable("id") Long id ,
                                                  @Valid @RequestBody CreateUpdateTenantDTO createUpdateTenantDTO,
                                                  @AuthenticationPrincipal(expression = "claims['email']") String username){

        return this.tenantService.updateTenant(id, createUpdateTenantDTO, username);
    }
}
