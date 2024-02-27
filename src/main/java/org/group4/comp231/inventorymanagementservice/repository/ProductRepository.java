package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<ProductSummaryInfo> findAllBy();
}