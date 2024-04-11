package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Account;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountSummaryInfo;
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
    private final StaticCodeService staticCodeService;
    private final String ACCOUNT_TYPE_CUSTOMER = "customer";
    private final String ACCOUNT_TYPE_VENDOR = "vendor";

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, TenantIdentifierResolver tenantIdentifierResolver, StaticCodeService staticCodeService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.staticCodeService = staticCodeService;
    }

    @Transactional
    public AccountSummaryInfo getAccount(Long id) {
            return accountRepository.findAccountById(id);
    }

    @Transactional
    public List<AccountSummaryInfo> getAllAccount(String type) {

        Long accountTypeCodeValueId = this.getAccountTypeCodeValueId(type);

        if (accountTypeCodeValueId == null) {
            return this.accountRepository.findAllBy();
        } else {
            return accountRepository.findByAccountType(accountTypeCodeValueId);
        }

    }

    public void createAccount(AccountDto dto, String createdBy) {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();
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
    public void updateAccount(Long categoryId, AccountDto dto, String updatedBy) throws Exception {

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        Optional<Account> account = this.accountRepository.findByTenantAndId(tenantId, categoryId);

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

        Long tenantId = this.tenantIdentifierResolver.resolveCurrentTenantIdentifier();

        Optional<Account> toBeDeleted = this.accountRepository.findByTenantAndId(tenantId, id);
        if (toBeDeleted.isEmpty()) {
            return;
        }

        this.accountRepository.deleteById(id);
    }

    private Long getAccountTypeCodeValueId(String type) {

        Long codeValueId = null;

        if(type == null) {
            return  codeValueId;
        }

        if(type.equals(ACCOUNT_TYPE_CUSTOMER)) {
            codeValueId = this.staticCodeService.SELLER_ACCOUNT_CODE_VALUE_ID;
        }
        else if (type.equals(ACCOUNT_TYPE_VENDOR)) {
            codeValueId = this.staticCodeService.VENDOR_ACCOUNT_CODE_VALUE_ID;
        }
        return  codeValueId;
    }
}
