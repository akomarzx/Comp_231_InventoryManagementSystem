package org.group4.comp231.inventorymanagementservice.dto.address;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Address}
 */
public interface AddressSummaryInfo {
    Long getId();

    String getAddressLine1();

    String getAddressLine2();

    String getCity();

    Long getProvince();

    Long getCountry();

    String getPrimaryPhone();
}