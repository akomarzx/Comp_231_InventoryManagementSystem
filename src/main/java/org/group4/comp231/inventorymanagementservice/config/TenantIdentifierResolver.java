package org.group4.comp231.inventorymanagementservice.config;

import lombok.Setter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

    private Long currentTenant = 0L;

    @Override
    public Long resolveCurrentTenantIdentifier() {
        return currentTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    @Override
    public boolean isRoot(Long tenantId) {
        return CurrentTenantIdentifierResolver.super.isRoot(tenantId);
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

}