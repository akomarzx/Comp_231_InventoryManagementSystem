package org.group4.comp231.inventorymanagementservice.services;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.domain.Tenant;
import org.group4.comp231.inventorymanagementservice.dto.tenant.CreateUpdateTenantDTO;
import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.mapper.TenantMapper;
import org.group4.comp231.inventorymanagementservice.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final UserService userService;

    public TenantService(TenantRepository tenantRepository, TenantMapper tenantMapper, UserService userService) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.userService = userService;
    }

    public  TenantDto getTenant(Long id) {

        Optional<Tenant> tenant = this.tenantRepository.findById(id);

        return tenant.map(this.tenantMapper::toDto).orElse(null);
    }

    public List<TenantDto> getAllTenants() {

        List<Tenant> tenants = this.tenantRepository.findAll();
        List<TenantDto> tenantDTOList = new ArrayList<>();

        for (Tenant tenant : tenants) {
            tenantDTOList.add(this.tenantMapper.toDto(tenant));
        }

        return tenantDTOList;
    }

    @Transactional(rollbackOn = {WebClientResponseException.class, Exception.class})
    public void createTenant(UserRegistrationDto dto) throws Exception {

        Tenant newTenant = new Tenant();

        newTenant.setCreatedBy(dto.getEmail());
        newTenant.setLabel(dto.getCompanyName());
        newTenant.setPrimaryEmail(dto.getEmail());
        newTenant.setCreatedAt(Instant.now());

        newTenant = this.tenantRepository.save(newTenant);

        //After Creating New Tenant - Create the new user in the IAM platform
        this.userService.registerNewUser(dto, newTenant.getId());

    }

    public TenantDto updateTenant(Long id, CreateUpdateTenantDTO dto, String username){

        Optional<Tenant> tenant = this.tenantRepository.findById(id);

        if (tenant.isEmpty()) {
            return null;
        }

        tenant.get().setLabel(dto.companyName());
        tenant.get().setUpdatedAt(Instant.now());
        tenant.get().setUpdatedBy(username);

        this.tenantRepository.save(tenant.get());

        return this.tenantMapper.toDto(tenant.get());
    }
}
