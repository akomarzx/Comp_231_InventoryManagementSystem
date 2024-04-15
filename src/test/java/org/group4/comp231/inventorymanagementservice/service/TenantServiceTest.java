package org.group4.comp231.inventorymanagementservice.service;

import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.Tenant;
import org.group4.comp231.inventorymanagementservice.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TenantIdentifierResolver.class)
)
class TenantServiceTest {

    @Autowired
    TenantRepository repository;

    @Test
    void createTenant() {
        Tenant newTenant = new Tenant();
        newTenant.setCreatedAt(Instant.now());
        newTenant.setCreatedBy("Test");
        newTenant.setLabel("Test Company");
        newTenant.setPrimaryEmail("Test@Mail.com");
        newTenant = repository.save(newTenant);
        assertNotNull(newTenant);
    }
}