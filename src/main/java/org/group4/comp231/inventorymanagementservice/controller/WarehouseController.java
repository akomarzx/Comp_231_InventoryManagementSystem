package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseInfo;
import org.group4.comp231.inventorymanagementservice.service.WarehouseService;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<List<WarehouseInfo>> getAllWarehouse() {
        return new ResponseEntity<>(this.warehouseService.getWarehouse(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new warehouse")
    public ResponseEntity<ObjectUtils.Null> createWarehouse(@Validated(ValidationGroups.Create.class) @RequestBody WarehouseDto warehouseDto,
                                                           @AuthenticationPrincipal(expression = "claims['email']") String createdBy) {

        log.info(warehouseDto.toString());
        this.warehouseService.createWarehouse(warehouseDto, createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update existing warehouse")
    public ResponseEntity<ObjectUtils.Null> updateWarehouse(@Valid @RequestBody WarehouseDto updateWarehouseDto,
                                                            @NotNull @PathVariable("id") Long id,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String updatedBy) throws Exception {

       this.warehouseService.updateWarehouse(id, updateWarehouseDto, updatedBy);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing warehouse")
    public ResponseEntity<ObjectUtils.Null> deleteWarehouse(@NotNull @PathVariable("id") Long id) throws Exception {

        this.warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }
}
