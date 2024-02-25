package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.services.AWSS3Service;
import org.group4.comp231.inventorymanagementservice.services.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/public")
@Tag(name = "Public API", description = "All Public APIs available")
public class PublicController {

    private final TenantService tenantService;
    private final AWSS3Service awss3Service;

    public PublicController(TenantService tenantService, AWSS3Service awss3Service) {
        this.tenantService = tenantService;
        this.awss3Service = awss3Service;
    }

    @PostMapping("/tenant")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Registration Endpoint for new business to register into the platform.")
    public ResponseEntity<ObjectUtils.Null> createTenant(@Valid @RequestBody UserRegistrationDto dto) throws Exception {
        this.tenantService.createTenant(dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return awss3Service.uploadFile(file.getOriginalFilename(), file);
    }
}
