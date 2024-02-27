package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.CreateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.UpdateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseInfo;
import org.group4.comp231.inventorymanagementservice.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Warehouse/Locations", description = "Endpoints for managing customer's location")
public class WarehouseController extends BaseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    @Operation(description = "Get All Warehouse for user")
    public ResponseEntity<List<WarehouseInfo>> getAllWarehouse(@AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {
        return new ResponseEntity<>(this.warehouseService.getWarehouse(Long.parseLong(tenantId)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new warehouse")
    public ResponseEntity<ObjectUtils.Null> createWarehouse(@Valid @RequestBody CreateWarehouseDto createWarehouseDto,
                                                           @AuthenticationPrincipal(expression = "claims['email']") String createdBy,
                                                           @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {

        log.info(createWarehouseDto.toString());
        this.warehouseService.createWarehouse(createWarehouseDto, Long.parseLong(tenantId), createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update existing warehouse")
    public ResponseEntity<WarehouseDto> updateWarehouse(@Valid @RequestBody UpdateWarehouseDto updateWarehouseDto,
                                                      @NotNull @PathVariable("id") Long id,
                                                      @AuthenticationPrincipal(expression = "claims['email']") String updatedBy,
                                                      @AuthenticationPrincipal(expression = "claims['tenant_id']") String tenantId) {

        WarehouseDto warehouseDto = this.warehouseService.updateWarehouse(id, updateWarehouseDto, Long.parseLong(tenantId), updatedBy);

        if (warehouseDto != null) {
            return new ResponseEntity<>(warehouseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
