package org.group4.comp231.inventorymanagementservice.repository;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryByLocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findAByProductAndId(@NotNull Product product, Long id);

    List<InventoryByLocationInfo> findAllLocationProductSummaryByProductId(Long productId);

    List<Inventory> findByProductId(Long productId);
}