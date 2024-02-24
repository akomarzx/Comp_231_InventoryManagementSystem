package org.group4.comp231.inventorymanagementservice.services;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Account;
import org.group4.comp231.inventorymanagementservice.domain.Warehouse;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.CreateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.UpdateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.CreateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.UpdateWarehouseDto;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseDto;
import org.group4.comp231.inventorymanagementservice.mapper.account.AccountMapper;
import org.group4.comp231.inventorymanagementservice.mapper.warehouse.WarehouseMapper;
import org.group4.comp231.inventorymanagementservice.repository.AccountRepository;
import org.group4.comp231.inventorymanagementservice.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private static final Log log = LogFactory.getLog(AccountService.class);
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Transactional
    public List<AccountDto> getAllAccount(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        List<Account> warehouses = this.accountRepository.findAll();
        return warehouses.stream().map(accountMapper::toDto).collect(Collectors.toList());
    }

    public void createAccount(CreateAccountDto dto, Long tenantId, String createdBy) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Account newAccount = this.accountMapper.partialUpdate(dto, new Account());
        newAccount.getAddress().setTenant(tenantId);
        newAccount.getAddress().setCreatedAt(Instant.now());
        newAccount.getAddress().setCreatedBy(createdBy);
        newAccount.setTenant(tenantId);
        newAccount.setCreatedBy(createdBy);
        newAccount.setCreatedAt(Instant.now());
        this.accountRepository.save(newAccount);
    }

    @Transactional
    public AccountDto updateAccount(Long categoryId, UpdateAccountDto dto, Long tenantId, String updatedBy) {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Optional<Account> account = this.accountRepository.findById(categoryId);

        if (account.isPresent()) {

            Account entity = this.accountMapper.partialUpdate(dto, account.get());
            entity.getAddress().setUpdatedAt(Instant.now());
            entity.getAddress().setUpdatedBy(updatedBy);
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.accountRepository.save(entity);

            return this.accountMapper.toDto(entity);

        } else {
            return null;
        }
    }

    public void deleteAccount(Long id) {
        this.accountRepository.deleteById(id);
    }
}
