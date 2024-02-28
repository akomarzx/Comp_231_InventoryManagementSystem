package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventorySummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Page<InventorySummaryInfo> findAllBy(Pageable pageable);
}