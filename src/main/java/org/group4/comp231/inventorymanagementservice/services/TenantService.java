package org.group4.comp231.inventorymanagementservice.services;

import org.group4.comp231.inventorymanagementservice.domain.Tenant;
import org.group4.comp231.inventorymanagementservice.dto.tenant.CreateUpdateTenantDTO;
import org.group4.comp231.inventorymanagementservice.dto.tenant.TenantDTO;
import org.group4.comp231.inventorymanagementservice.mapper.TenantMapper;
import org.group4.comp231.inventorymanagementservice.repository.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    public TenantService(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    public List<TenantDTO> getAllTenants() {

        List<Tenant> tenants = this.tenantRepository.findAll();
        List<TenantDTO> tenantDTOList = new ArrayList<>();

        for(Tenant tenant : tenants) {
            tenantDTOList.add(this.tenantMapper.toDto(tenant));
        }

        return tenantDTOList;
    }

    public TenantDTO createTenant(CreateUpdateTenantDTO createUpdateTenantDTO, String username) {

        Tenant newTenant = new Tenant();

        newTenant.setCreatedBy(username);
        newTenant.setCreatedAt(Instant.now());
        newTenant.setLabel(createUpdateTenantDTO.label());

        newTenant = this.tenantRepository.save(newTenant);

        return this.tenantMapper.toDto(newTenant);
    }

    public ResponseEntity<TenantDTO> updateTenant(Long id, CreateUpdateTenantDTO dto, String username){

        Optional<Tenant> tenant = this.tenantRepository.findById(id);

        if(tenant.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        tenant.get().setLabel(dto.label());
        tenant.get().setUpdatedAt(Instant.now());
        tenant.get().setUpdatedBy(username);

        this.tenantRepository.save(tenant.get());

        return new ResponseEntity<>(this.tenantMapper.toDto(tenant.get()), HttpStatus.OK);
    }
}
