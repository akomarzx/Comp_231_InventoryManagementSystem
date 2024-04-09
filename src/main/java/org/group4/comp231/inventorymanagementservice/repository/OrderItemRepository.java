package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}