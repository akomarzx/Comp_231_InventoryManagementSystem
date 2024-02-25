package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Inventory", description = "Endpoints for Managing Inventory")
public class InventoryController {

    private static final Log log = LogFactory.getLog(InventoryController.class);
    private final ProductRepository productRepository;
    private final TenantIdentifierResolver identifierResolver;
    public InventoryController(ProductRepository productRepository, TenantIdentifierResolver identifierResolver) {
        this.productRepository = productRepository;
        this.identifierResolver = identifierResolver;
    }

    @GetMapping()
    @Transactional
    public ResponseEntity<List<Product>> getAllProduct() {
        this.identifierResolver.setCurrentTenant(1L);
        List<Product> products = this.productRepository.findAll();
        log.info(products.size());
        return ResponseEntity.ok(products);
    }
}
