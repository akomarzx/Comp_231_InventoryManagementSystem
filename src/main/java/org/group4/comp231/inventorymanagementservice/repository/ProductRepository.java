package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    public Page<ProductSummaryInfo> findAllBy(Pageable pageable);
}