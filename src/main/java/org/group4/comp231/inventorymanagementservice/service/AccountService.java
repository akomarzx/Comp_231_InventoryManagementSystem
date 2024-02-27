package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Account;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountSummaryInfo;
import org.group4.comp231.inventorymanagementservice.dto.account.CreateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.UpdateAccountDto;
import org.group4.comp231.inventorymanagementservice.mapper.account.AccountMapper;
import org.group4.comp231.inventorymanagementservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService extends BaseService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Transactional
    public List<AccountSummaryInfo> getAllAccount(@NotNull Long tenantId) {
        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        return this.accountRepository.findAllBy();
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
    public void updateAccount(Long categoryId, UpdateAccountDto dto, Long tenantId, String updatedBy) throws Exception {

        this.tenantIdentifierResolver.setCurrentTenant(tenantId);
        Optional<Account> account = this.accountRepository.findById(categoryId);

        if (account.isPresent()) {

            Account entity = this.accountMapper.partialUpdate(dto, account.get());
            entity.getAddress().setUpdatedAt(Instant.now());
            entity.getAddress().setUpdatedBy(updatedBy);
            entity.setUpdatedBy(updatedBy);
            entity.setUpdatedAt(Instant.now());

            this.accountRepository.save(entity);
        } else {
            throw new Exception("Entity not found");
        }
    }

    public void deleteAccount(Long id) {
        this.accountRepository.deleteById(id);
    }
}
