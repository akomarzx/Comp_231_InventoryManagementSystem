package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.domain.ViewProductSummary;
import org.group4.comp231.inventorymanagementservice.dto.inventory.CreateInventoryDto;
import org.group4.comp231.inventorymanagementservice.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ObjectUtils.Null> createInventory(@Valid @RequestBody CreateInventoryDto createInventoryDto,
                                                            @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {

        this.inventoryService.createInventory(createInventoryDto, createdBy, null);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
