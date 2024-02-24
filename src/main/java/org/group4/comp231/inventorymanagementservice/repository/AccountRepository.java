package org.group4.comp231.inventorymanagementservice.repository;

import org.group4.comp231.inventorymanagementservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
}