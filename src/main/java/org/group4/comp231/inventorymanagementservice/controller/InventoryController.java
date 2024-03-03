package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.domain.ViewProductSummary;
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
    public Page<ViewProductSummary> getAllProduct(@RequestParam int page,
                                                  @RequestParam int size) {

        return this.inventoryService.getProduct(page, size);
    }

    @PostMapping
    public ResponseEntity<ObjectUtils.Null> createInventory(@Validated(Create.class) @RequestBody InventoryDto inventoryDto,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {

        this.inventoryService.createInventory(inventoryDto, createdBy, null);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ObjectUtils.Null> updateInventory(@NotNull @PathVariable("productId") Long productId,
                                                            @Validated(Update.class) @RequestBody InventoryDto inventoryDto,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {


        this.inventoryService.updateInventory(productId, inventoryDto, createdBy);

        return new ResponseEntity<>(null, HttpStatus.OK);

    }
}
