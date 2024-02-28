package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.domain.Product;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.domain.category.ProductCategory;
import org.group4.comp231.inventorymanagementservice.domain.category.ProductCategoryId;
import org.group4.comp231.inventorymanagementservice.dto.inventory.CreateInventoryDto;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventorySummaryInfo;
import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.group4.comp231.inventorymanagementservice.repository.InventoryRepository;
import org.group4.comp231.inventorymanagementservice.repository.ProductCategoryRepository;
import org.group4.comp231.inventorymanagementservice.repository.ProductRepository;
import org.group4.comp231.inventorymanagementservice.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class InventoryService extends BaseService{

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final ProductCategoryRepository productCategoryRepository;
    private final WarehouseRepository warehouseRepository;
    public InventoryService(ProductRepository productRepository, InventoryRepository inventoryRepository, TenantIdentifierResolver tenantIdentifierResolver, ProductCategoryRepository productCategoryRepository, WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.productCategoryRepository = productCategoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Page<InventorySummaryInfo> getProduct(int page, int size, Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Pageable pageRequest = PageRequest.of(page, size);
        return this.inventoryRepository.findAllBy(pageRequest);
    }

    @Transactional
    public void createInventory(CreateInventoryDto createInventoryDto, String createdBy, Long tenantId, Long existingProductId) throws Exception {

        Product product = null;

        if(createInventoryDto.productCategoryIds().size() > 5) {
            throw new Exception("Too many categories");
        }

        if(existingProductId != null) {
            Optional<Product> existing = this.productRepository.findById(existingProductId);
            if(existing.isEmpty()) {
                throw new Exception("Existing product not found");
            } else {
                product = existing.get();
            }
        } else {
            product = this.mapProductFromRequest(createInventoryDto, tenantId, createdBy);
            product = this.productRepository.save(product);
            for (Long categoryId : createInventoryDto.productCategoryIds()) {
                ProductCategoryId productCategoryId = new ProductCategoryId();
                productCategoryId.setCategoryId(categoryId);
                productCategoryId.setProductId(product.getId());
                productCategoryId.setTenantId(tenantId);
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(productCategoryId);
                productCategory.setCreatedAt(Instant.now());
                productCategory.setCreatedBy(createdBy);
                this.productCategoryRepository.save(productCategory);
            }
        }

        Inventory newInventory = this.mapInventoryFromRequest(createInventoryDto, tenantId, createdBy, product);
        this.inventoryRepository.save(newInventory);
    }

    private Product mapProductFromRequest(CreateInventoryDto createInventoryDto, Long tenantId, String createdBy) {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);

        Product product = new Product();
        product.setTenant(tenantId);
        product.setCreatedAt(Instant.now());
        product.setCreatedBy(createdBy);
        product.setPrice(new BigDecimal(String.valueOf(createInventoryDto.productPrice())));
        product.setLabel(createInventoryDto.productLabel());

        if(createInventoryDto.productUpi() != null) {
            product.setUpi(createInventoryDto.productUpi());
        }

        if(createInventoryDto.productImageUrl() != null) {
            product.setImageUrl(createInventoryDto.productImageUrl());
        }

        if(createInventoryDto.productUpi() != null) {
            product.setUpi(createInventoryDto.productUpi());
        }

        return product;
    }

    private Inventory mapInventoryFromRequest(CreateInventoryDto createInventoryDto, Long tenantId, String createdBy, @NotNull  Product product) throws Exception {

        Inventory newInventory = new Inventory();
        newInventory.setTenant(tenantId);
        newInventory.setCreatedAt(Instant.now());
        newInventory.setCreatedBy(createdBy);
        newInventory.setProduct(product);
        newInventory.setSku(createInventoryDto.sku());
        newInventory.setQuantity(createInventoryDto.quantity());

        Optional<Warehouse> selectedWarehouse = this.warehouseRepository.findById(createInventoryDto.warehouse());

        if(selectedWarehouse.isPresent()) {
            newInventory.setWarehouse(selectedWarehouse.get());
        } else {
            throw new Exception("Selected Warehouse is invalid");
        }

        if(createInventoryDto.minimumQuantity() != null) {
            newInventory.setMinimumQuantity(createInventoryDto.minimumQuantity());
        }

        if(createInventoryDto.maximumQuantity() != null) {
            newInventory.setMaximumQuantity(createInventoryDto.maximumQuantity());
        }
        // TODO BUG - Fix notes not populating in db
        if(createInventoryDto.notes() != null) {
            newInventory.setNotes(newInventory.getNotes());
        }

        return newInventory;
    }
}
