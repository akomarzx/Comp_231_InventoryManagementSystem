package org.group4.comp231.inventorymanagementservice.service;

import org.group4.comp231.inventorymanagementservice.domain.OrderStatus;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatusChange;
import org.group4.comp231.inventorymanagementservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {


    @Autowired private OrderService orderService;

    @Test
    void checkIfOrderStatusChangeExist() {
        List<OrderStatusChange> statusChanges = new ArrayList<>();

        OrderStatusChange orderStatusChange1 = new OrderStatusChange();
        orderStatusChange1.setOrderStatus(OrderStatus.PURCHASE_ORDER_CLOSED);
        OrderStatusChange orderStatusChange2 = new OrderStatusChange();
        orderStatusChange1.setOrderStatus(OrderStatus.PURCHASE_ORDER_PENDING);

        statusChanges.add(orderStatusChange1);
        statusChanges.add(orderStatusChange2);

        assertTrue(orderService.checkIfOrderStatusChangeExist(OrderStatus.PURCHASE_ORDER_PENDING, statusChanges));
    }

    @Test
    void checkIfThereAreLaterStagesOrStageAlreadyExist() {
        List<OrderStatusChange> statusChanges = new ArrayList<>();

        OrderStatusChange orderStatusChange1 = new OrderStatusChange();
        orderStatusChange1.setOrderStatus(OrderStatus.PURCHASE_ORDER_CLOSED);
        OrderStatusChange orderStatusChange3 = new OrderStatusChange();
        orderStatusChange1.setOrderStatus(OrderStatus.PURCHASE_ORDER_PO_SENT);

        statusChanges.add(orderStatusChange1);
        statusChanges.add(orderStatusChange3);

        assertTrue(orderService.checkIfThereAreLaterStagesOrStageAlreadyExist(OrderStatus.PURCHASE_ORDER_PENDING, statusChanges));
    }
}