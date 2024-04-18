package org.group4.comp231.inventorymanagementservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.TenantId;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Mapping for DB view
 */
@Entity
@Immutable
@Table(name = "view_product_summary")
public class ViewProductSummary {

    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @TenantId
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "upi")
    private Long upi;

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imgUrl;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "quantity", precision = 41)
    private BigDecimal quantity;

    @Lob
    @Column(name = "locations")
    private String locations;

    @Column(name = "category_label")
    private String categories;

    @Size(max = 255)
    @NotNull
    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "purchase_price", precision = 10)
    private BigDecimal purchasePrice;

    @NotNull
    @Column(name = "selling_price", nullable = false, precision = 10)
    private BigDecimal sellingPrice;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    public Long getInventoryId() {
        return inventoryId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public String getSku() {
        return sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getUpi() {
        return upi;
    }

    public void setUpi(Long upi) {
        this.upi = upi;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}