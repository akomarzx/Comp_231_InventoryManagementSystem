package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.persistence.Transient;
import org.group4.comp231.inventorymanagementservice.domain.Order;
import org.group4.comp231.inventorymanagementservice.domain.OrderItem;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatus;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatusChange;

import java.util.Set;

/**
 * Projection for {@link Order}
 */
public interface OrderInfo {
    Long getId();

    String getOrderReferenceNumber();

    Long getOrderType();

    OrderStatus getOrderStatus();

    String getNotes();

    Set<OrderItem> getOrderItems();

    Set<OrderStatusChange> getOrderStatusChanges();

}