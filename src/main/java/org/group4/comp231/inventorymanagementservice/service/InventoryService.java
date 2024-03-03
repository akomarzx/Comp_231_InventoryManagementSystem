package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.domain.ViewProductSummary;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryByLocationInfo;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryDto;
import org.group4.comp231.inventorymanagementservice.mapper.inventory.InventoryMapper;
import org.group4.comp231.inventorymanagementservice.mapper.inventory.ProductMapper;
import org.group4.comp231.inventorymanagementservice.repository.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService extends BaseService{

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final WarehouseRepository warehouseRepository;
    private final ViewProductSummaryRepository viewProductListRepository;

    public InventoryService(ProductRepository productRepository, InventoryRepository inventoryRepository, TenantIdentifierResolver tenantIdentifierResolver, WarehouseRepository warehouseRepository, ViewProductSummaryRepository viewProductListRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.warehouseRepository = warehouseRepository;
        this.viewProductListRepository = viewProductListRepository;
    }

    public Page<ViewProductSummary> getProduct(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return this.viewProductListRepository.findAllBy(pageRequest);
    }

    @Transactional
    public void createInventory(InventoryDto inventoryDto, String createdBy, Long existingProductId) throws Exception {

        Product product = null;
        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        if(existingProductId != null) {
            Optional<Product> existing = this.productRepository.findByTenantAndId(tenantId, existingProductId);
            if(existing.isEmpty()) {
                throw new Exception("Existing product not found");
            } else {
                product = existing.get();
            }
        } else {
            product = this.mapProductFromRequest(inventoryDto, tenantId, createdBy);
            product = this.productRepository.save(product);
        }

        Inventory newInventory = this.mapInventoryFromRequest(inventoryDto, tenantId, createdBy, product);

        this.inventoryRepository.save(newInventory);
    }

    private Product mapProductFromRequest(InventoryDto inventoryDto, Long tenantId, String createdBy) {

        ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

        Product product = productMapper.toEntity(inventoryDto.product());
        product.setTenant(tenantId);
        product.setCreatedAt(Instant.now());
        product.setCreatedBy(createdBy);

        return product;
    }

    private Inventory mapInventoryFromRequest(InventoryDto inventoryDto, Long tenantId, String createdBy, @NotNull  Product product) throws Exception {

        Inventory newInventory = new Inventory();
        newInventory.setTenant(tenantId);
        newInventory.setCreatedAt(Instant.now());
        newInventory.setCreatedBy(createdBy);
        newInventory.setProduct(product);
        newInventory.setQuantity(inventoryDto.quantity());

        Optional<Warehouse> selectedWarehouse = this.warehouseRepository.findByTenantAndId(tenantId, inventoryDto.warehouse());

        if(selectedWarehouse.isPresent()) {
            newInventory.setWarehouse(selectedWarehouse.get());
        } else {
            throw new Exception("Selected Warehouse was invalid");
        }

        if(inventoryDto.minimumQuantity() != null) {
            newInventory.setMinimumQuantity(inventoryDto.minimumQuantity());
        }

        if(inventoryDto.maximumQuantity() != null) {
            newInventory.setMaximumQuantity(inventoryDto.maximumQuantity());
        }
        // TODO BUG - Fix notes not populating in db
        if(inventoryDto.notes() != null) {
            newInventory.setNotes(inventoryDto.notes());
        }

        return newInventory;
    }

    @Transactional
    public void updateInventory(Long productId, InventoryDto inventoryDto, String updatedBy) throws Exception {

        ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
        InventoryMapper inventoryMapper = Mappers.getMapper(InventoryMapper.class);

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        Optional<Product> product = this.productRepository.findByTenantAndId(tenantId, productId);

        if(product.isEmpty()) {
            throw new Exception("Product Entity Not Found.");
        }

        if(inventoryDto.inventoryId() != null) {

            Optional<Inventory> inventory = this.inventoryRepository.findAByProductAndId(product.get(), inventoryDto.inventoryId());

            if(inventory.isEmpty()) {
                throw new Exception("Inventory Entity Not Found.");
            }

            Inventory updatedInventory = inventoryMapper.partialUpdate(inventoryDto, inventory.get());
            updatedInventory.setUpdatedAt(Instant.now());
            updatedInventory.setUpdatedBy(updatedBy);

            this.inventoryRepository.save(updatedInventory);
        }

        Product updatedProduct = productMapper.partialUpdate(inventoryDto.product(), product.get());
        updatedProduct.setUpdatedAt(Instant.now());
        updatedProduct.setUpdatedBy(updatedBy);

        this.productRepository.save(updatedProduct);
    }

    public List<InventoryByLocationInfo> getInventorySummaryByLocation(Long productId) {
        return this.inventoryRepository.findAllLocationProductSummaryByProductId(productId);
    }

    @Transactional
    public void addInventoryItemToOtherLocation(Long productId, Long warehouseId, InventoryDto dto, String createdBy) throws Exception {

        List<InventoryByLocationInfo> inventoriesByProduct = this.inventoryRepository.findAllLocationProductSummaryByProductId(productId);

        if(inventoriesByProduct.stream().anyMatch(summary -> summary.getWarehouse().getId().equals(warehouseId))) {
            throw new Exception("Inventory already exist in specified location");
        }
        InventoryDto inventoryDto = new InventoryDto(null, null, warehouseId, dto.quantity(), dto.minimumQuantity(), dto.maximumQuantity(), dto.notes());
        this.createInventory(inventoryDto, createdBy, productId);
    }

    @Transactional
    public void deleteInventory(Long productId) {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
        Optional<Product> product = this.productRepository.findByTenantAndId(tenantId, productId);

        if(product.isEmpty()) {
            return;
        } else {
            List<Inventory> inventoryList = this.inventoryRepository.findByProductId(productId);
            if(inventoryList.isEmpty()) {
                return;
            } else {
                this.inventoryRepository.deleteAllById(inventoryList.stream().map(Inventory::getId).collect(Collectors.toList()));
                this.productRepository.deleteById(productId);
            }
        }
    }
}
