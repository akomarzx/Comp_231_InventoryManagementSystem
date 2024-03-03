package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseInfo;
import org.group4.comp231.inventorymanagementservice.mapper.warehouse.WarehouseMapper;
import org.group4.comp231.inventorymanagementservice.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService extends BaseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Transactional
    public List<WarehouseInfo> getWarehouse() {
        return this.warehouseRepository.findAllBy();
    }

    public void createWarehouse(WarehouseDto dto, String createdBy) {
        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
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
    public void updateWarehouse(Long categoryId, WarehouseDto dto, String updatedBy) throws Exception {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        Optional<Warehouse> category = this.warehouseRepository.findByTenantAndId(tenantId, categoryId);

        if(category.isPresent()) {

            Warehouse entity = this.warehouseMapper.partialUpdate(dto, category.get());
            entity.getAddress().setUpdatedAt(Instant.now());
            entity.getAddress().setUpdatedBy(updatedBy);
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.warehouseRepository.save(entity);

        } else {
            throw new Exception("Entity not found");
        }
    }

    @Transactional
    public void deleteWarehouse(Long id) {
        this.warehouseRepository.deleteById(id);
    }
}
