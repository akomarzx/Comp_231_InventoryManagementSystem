package org.group4.comp231.inventorymanagementservice.mapper.account;

import org.group4.comp231.inventorymanagementservice.dto.account.UpdateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.CreateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountDto;
import org.group4.comp231.inventorymanagementservice.domain.Account;
import org.group4.comp231.inventorymanagementservice.mapper.address.AddressMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface AccountMapper {
    Account toEntity(AccountDto accountDto);

    AccountDto toDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);

    Account toEntity(CreateAccountDto createAccountDto);

    CreateAccountDto toCreateAccountDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(CreateAccountDto createAccountDto, @MappingTarget Account account);

    Account toEntity(UpdateAccountDto updateAccountDto);

    UpdateAccountDto toUpdateAccountDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(UpdateAccountDto updateAccountDto, @MappingTarget Account account);
}