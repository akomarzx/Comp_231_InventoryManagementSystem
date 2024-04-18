package org.group4.comp231.inventorymanagementservice.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatus;

import java.util.stream.Stream;

/**
 * Converter for OrderStatus Enum
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Long> {

    @Override
    public Long convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Long code) {
        if (code == null) {
            return null;
        }

        return Stream.of(OrderStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
