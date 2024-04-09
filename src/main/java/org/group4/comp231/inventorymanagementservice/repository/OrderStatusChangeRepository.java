package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.OrderStatusChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusChangeRepository extends JpaRepository<OrderStatusChange, Long> {
    List<OrderStatusChange> findByOrder(Long order);
}