package org.group4.comp231.inventorymanagementservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicAPI {

    @GetMapping
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
