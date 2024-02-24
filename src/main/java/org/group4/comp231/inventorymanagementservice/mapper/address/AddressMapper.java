package org.group4.comp231.inventorymanagementservice.mapper.address;

import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;
import org.group4.comp231.inventorymanagementservice.domain.Address;
import org.group4.comp231.inventorymanagementservice.dto.address.CreateAddressDto;
import org.group4.comp231.inventorymanagementservice.dto.address.UpdateAddressDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);

    AddressDto toDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressDto addressDto, @MappingTarget Address address);

    Address toEntity(CreateAddressDto createAddressDto);

    CreateAddressDto toCreateAddressDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(CreateAddressDto createAddressDto, @MappingTarget Address address);

    Address toEntity(UpdateAddressDto updateAddressDto);

    UpdateAddressDto toUpdateAddressDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(UpdateAddressDto updateAddressDto, @MappingTarget Address address);
}