package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountSummaryInfo;
import org.group4.comp231.inventorymanagementservice.dto.account.CreateAccountDto;
import org.group4.comp231.inventorymanagementservice.dto.account.UpdateAccountDto;
import org.group4.comp231.inventorymanagementservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Account", description = "Endpoints for Managing Vendor and Seller Account")
public class AccountController extends BaseController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(description = "Get All Accounts")
    public ResponseEntity<List<AccountSummaryInfo>> getAllAccount() {
        return new ResponseEntity<>(this.accountService.getAllAccount(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new Seller/Vendor Account")
    public ResponseEntity<ObjectUtils.Null> createAccount(@Valid @RequestBody CreateAccountDto accountDto,
                                                          @AuthenticationPrincipal(expression = "claims['email']") String createdBy) {
        this.accountService.createAccount(accountDto, createdBy);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update Existing Vendor/Seller Information")
    public ResponseEntity<ObjectUtils.Null> updateAccount(@Valid @RequestBody UpdateAccountDto dto,
                                                          @NotNull @PathVariable("id") Long id,
                                                          @AuthenticationPrincipal(expression = "claims['email']") String updatedBy) throws Exception {

        this.accountService.updateAccount(id, dto, updatedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a Vendor/Seller Account")
    public ResponseEntity<ObjectUtils.Null> deleteAccount(@NotNull @PathVariable("id") String accountId) {
        this.accountService.deleteAccount(Long.parseLong(accountId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}