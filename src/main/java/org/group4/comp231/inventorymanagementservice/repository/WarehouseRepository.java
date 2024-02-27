package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<WarehouseInfo> findAllBy();
}