package org.group4.comp231.inventorymanagementservice.dto.account;

import org.group4.comp231.inventorymanagementservice.dto.address.AddressSummaryInfo;

import java.time.Instant;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Account}
 */
public interface AccountSummaryInfo {
    Long getId();

    Long getAccountType();

    String getLabel();

    String getEmail();

    AddressSummaryInfo getAddress();

    Instant getCreated();
}