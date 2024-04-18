package org.group4.comp231.inventorymanagementservice.utility;

import org.group4.comp231.inventorymanagementservice.domain.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusConverterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convertToDatabaseColumn() {
        OrderStatusConverter converter = new OrderStatusConverter();
        Long code = converter.convertToDatabaseColumn(OrderStatus.SALES_ORDER_CLOSED);
        assertEquals(code, 300060L);
    }

    @Test
    void convertToEntityAttribute() {
        OrderStatusConverter converter = new OrderStatusConverter();
        OrderStatus status = converter.convertToEntityAttribute(300060L);
        assertEquals(status, OrderStatus.SALES_ORDER_CLOSED);
    }
}