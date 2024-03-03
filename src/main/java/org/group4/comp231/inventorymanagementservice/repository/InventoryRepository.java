package org.group4.comp231.inventorymanagementservice.repository;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventorySummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Page<InventorySummaryInfo> findAllBy(Pageable pageable);

    public Optional<Inventory> findByTenantAndId(Long tenantId, Long id);

    public Optional<Inventory> findAByProductAndId(@NotNull Product product, Long id);
}