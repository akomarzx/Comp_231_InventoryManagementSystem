package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Address;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}