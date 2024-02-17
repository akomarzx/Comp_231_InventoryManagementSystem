package org.group4.comp231.inventorymanagementservice.mapper;

import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDto;
import org.group4.comp231.inventorymanagementservice.domain.Tenant;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TenantMapper {
    @Mapping(source = "companyName", target = "label")
    Tenant toEntity(TenantDto tenantDto);

    @Mapping(source = "label", target = "companyName")
    TenantDto toDto(Tenant tenant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "companyName", target = "label")
    Tenant partialUpdate(TenantDto tenantDto, @MappingTarget Tenant tenant);
}