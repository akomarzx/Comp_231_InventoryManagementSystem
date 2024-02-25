package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.CreateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.UpdateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.mapper.warehouse.WarehouseMapper;
import org.group4.comp231.inventorymanagementservice.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private static final Log log = LogFactory.getLog(WarehouseService.class);

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Transactional
    public List<WarehouseDto> getWarehouse(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        List<Warehouse> warehouses = this.warehouseRepository.findAll();
        return warehouses.stream().map(warehouseMapper::toDto).collect(Collectors.toList());
    }

    public void createWarehouse(CreateWarehouseDto dto, Long tenantId, String createdBy) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Warehouse newWarehouse = this.warehouseMapper.partialUpdate(dto, new Warehouse());
        newWarehouse.getAddress().setTenant(tenantId);
        newWarehouse.getAddress().setCreatedAt(Instant.now());
        newWarehouse.getAddress().setCreatedBy(createdBy);
        newWarehouse.setTenant(tenantId);
        newWarehouse.setCreatedBy(createdBy);
        newWarehouse.setCreatedAt(Instant.now());
        this.warehouseRepository.save(newWarehouse);
    }

    @Transactional
    public WarehouseDto updateWarehouse(Long categoryId, UpdateWarehouseDto dto, Long tenantId, String updatedBy) {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Optional<Warehouse> category = this.warehouseRepository.findById(categoryId);

        if(category.isPresent()) {

            Warehouse entity = this.warehouseMapper.partialUpdate(dto, category.get());
            entity.getAddress().setUpdatedAt(Instant.now());
            entity.getAddress().setUpdatedBy(updatedBy);
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.warehouseRepository.save(entity);

            return this.warehouseMapper.toDto(entity);

        } else {
            return null;
        }
    }
}
