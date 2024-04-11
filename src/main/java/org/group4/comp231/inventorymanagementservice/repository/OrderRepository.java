package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Order;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<OrderInfo> findAllBy();

    List<Order> findByAccountAndTenant(Long account, Long tenant);

    List<Order> findByOrderTypeAndTenant(Long orderType, Long tenant);
}