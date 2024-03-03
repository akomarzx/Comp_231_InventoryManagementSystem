package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.domain.ViewProductSummary;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryByLocationInfo;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryDto;
import org.group4.comp231.inventorymanagementservice.service.InventoryService;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Update;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Inventory", description = "Endpoints for Managing Inventory")
public class InventoryController extends BaseController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(description = "View Current Inventory List")
    public Page<ViewProductSummary> getAllProduct(@RequestParam int page,
                                                  @RequestParam int size) {

        return this.inventoryService.getProduct(page, size);
    }

    @PostMapping
    @Operation(description = "Create New Inventory Item")
    public ResponseEntity<ObjectUtils.Null> createInventory(@Validated(Create.class) @RequestBody InventoryDto inventoryDto,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {

        this.inventoryService.createInventory(inventoryDto, createdBy, null);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @Operation(description = "Update Existing Inventory Item")
    public ResponseEntity<ObjectUtils.Null> updateInventory(@NotNull @PathVariable("productId") Long productId,
                                                            @Validated(Update.class) @RequestBody InventoryDto inventoryDto,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {


        this.inventoryService.updateInventory(productId, inventoryDto, createdBy);

        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @GetMapping("/{productId}/warehouse")
    @Operation(description = "View Breakdown of inventory Item per location")
    public ResponseEntity<List<InventoryByLocationInfo>> getInventoryByLocationInfo(@PathVariable("productId") Long productId) {

        List<InventoryByLocationInfo> byLocationInfoList = this.inventoryService.getInventorySummaryByLocation(productId);

        return new ResponseEntity<>(byLocationInfoList, HttpStatus.OK);
    }

    @PostMapping("{productId}/warehouse/{warehouseId}")
    @Operation(description = "Manually add Inventory Item to other location - only quantity is needed for this request")
    public ResponseEntity<ObjectUtils.Null> addInventoryToLocationManual(@PathVariable("productId") Long productId,
                                                                         @PathVariable("warehouseId") Long warehouseId,
                                                                         @RequestBody InventoryDto dto,
                                                                         @AuthenticationPrincipal(expression = "claims['email']") String createdBy)throws Exception {

        this.inventoryService.addInventoryItemToOtherLocation(productId, warehouseId, dto, createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ObjectUtils.Null> deleteInventory(@NotNull @PathVariable("productId") Long productId) {
        this.inventoryService.deleteInventory(productId);
        return ResponseEntity.noContent().build();
    }
}
