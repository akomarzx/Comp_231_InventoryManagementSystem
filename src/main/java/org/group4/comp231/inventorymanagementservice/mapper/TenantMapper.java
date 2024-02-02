package org.group4.comp231.inventorymanagementservice.mapper;

import org.group4.comp231.inventorymanagementservice.domain.Tenant;
import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TenantMapper {
    Tenant toEntity(TenantDTO tenantDTO);

    TenantDTO toDto(Tenant tenant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tenant partialUpdate(TenantDTO tenantDTO, @MappingTarget Tenant tenant);
}