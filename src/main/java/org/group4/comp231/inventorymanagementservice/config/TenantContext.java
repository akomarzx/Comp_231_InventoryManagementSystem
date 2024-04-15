package org.group4.comp231.inventorymanagementservice.config;

/**
 * Thread Safe Container for tenant ID
 */
public class TenantContext {
    private static ThreadLocal<Long> currentTenant = new InheritableThreadLocal<>();

    public static Long getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(Long tenant) {
        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}
