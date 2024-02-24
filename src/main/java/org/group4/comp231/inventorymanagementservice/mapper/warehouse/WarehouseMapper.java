package org.group4.comp231.inventorymanagementservice.mapper.warehouse;

import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.CreateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.UpdateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.mapper.address.AddressMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseDto warehouseDto);

    WarehouseDto toDto(Warehouse warehouse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Warehouse partialUpdate(WarehouseDto warehouseDto, @MappingTarget Warehouse warehouse);

    Warehouse toEntity(CreateWarehouseDto createWarehouseDto);

    CreateWarehouseDto toCreateWarehouseDto(Warehouse warehouse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Warehouse partialUpdate(CreateWarehouseDto createWarehouseDto, @MappingTarget Warehouse warehouse);

    Warehouse toEntity(UpdateWarehouseDto updateWarehouseDto);

    UpdateWarehouseDto toUpdateWarehouseDto(Warehouse warehouse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Warehouse partialUpdate(UpdateWarehouseDto updateWarehouseDto, @MappingTarget Warehouse warehouse);
}