package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.group4.comp231.inventorymanagementservice.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/inventory")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Inventory", description = "Endpoints for Managing Inventory")
public class InventoryController extends BaseController {

    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public Page<ProductSummaryInfo> getAllProduct(@RequestParam int page, @RequestParam int size) {
        return this.inventoryService.getProduct(page, size);
    }
}
