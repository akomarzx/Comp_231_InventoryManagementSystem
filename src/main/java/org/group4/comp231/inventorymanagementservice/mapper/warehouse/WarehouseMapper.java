package org.group4.comp231.inventorymanagementservice.mapper.warehouse;

import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.mapper.address.AddressMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseDto warehouseDto);

    WarehouseDto toDto(Warehouse warehouse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Warehouse partialUpdate(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);
}