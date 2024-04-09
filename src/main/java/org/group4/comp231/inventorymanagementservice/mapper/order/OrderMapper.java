package org.group4.comp231.inventorymanagementservice.mapper.order;

import org.group4.comp231.inventorymanagementservice.domain.Order;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);

    @AfterMapping
    default void linkOrderItems(@MappingTarget Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order.getId()));
    }

    OrderDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);
}