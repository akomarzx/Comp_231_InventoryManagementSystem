package org.group4.comp231.inventorymanagementservice.mapper.inventory;

import org.group4.comp231.inventorymanagementservice.domain.Inventory;
import org.group4.comp231.inventorymanagementservice.dto.inventory.InventoryDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "warehouse", ignore = true)
    Inventory toEntity(InventoryDto inventoryDto);

    @Mapping(target = "warehouse", ignore = true)
    InventoryDto toDto(Inventory inventory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "warehouse", ignore = true)
    Inventory partialUpdate(InventoryDto inventoryDto, @MappingTarget Inventory inventory);

}