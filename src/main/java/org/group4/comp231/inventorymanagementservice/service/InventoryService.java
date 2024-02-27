package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.group4.comp231.inventorymanagementservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService extends BaseService{

    private final ProductRepository productRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public InventoryService(ProductRepository productRepository, TenantIdentifierResolver tenantIdentifierResolver) {
        this.productRepository = productRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Transactional
    public List<ProductSummaryInfo> getProduct() {
        this.tenantIdentifierResolver.setCurrentTenant(1L);
        return this.productRepository.findAllBy();
    }
}
