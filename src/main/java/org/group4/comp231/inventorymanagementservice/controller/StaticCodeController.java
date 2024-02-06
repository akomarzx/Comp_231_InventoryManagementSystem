package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.services.StaticCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Static Code", description = "Static Codes inside of the API")
public class StaticCodeController {

    private final StaticCodeService staticCodeService;

    public StaticCodeController(StaticCodeService staticCodeService) {
        this.staticCodeService = staticCodeService;
    }

    @GetMapping
    public ResponseEntity<List<CodeBook>> getAllStaticValues(){
        List<CodeBook> valueList = this.staticCodeService.getAllStaticCode();
        return new ResponseEntity<>(valueList, HttpStatus.OK);
    }

}
